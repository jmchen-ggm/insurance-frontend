package com.bbinsurance.android.app.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.Application
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.plugin.comment.ui.CommentUI
import com.bbinsurance.android.app.plugin.learn.ui.LearnArticleUI
import com.bbinsurance.android.app.protocol.*
import com.bbinsurance.android.app.ui.adapter.BannerAdapter
import com.bbinsurance.android.app.ui.component.BannerBaseUIComponent
import com.bbinsurance.android.lib.Util
import com.bbinsurance.android.lib.log.BBLog
import com.bigkoo.convenientbanner.ConvenientBanner
import com.facebook.drawee.view.SimpleDraweeView


/**
 * Created by jiaminchen on 17/11/12.
 */
class HomeFragmentUI : Fragment(), BannerBaseUIComponent<BBInsurance> {

    val TAG = "BB.HomeFragmentUI"

    companion object {
        val ConfigHomeDataKey = "ConfigHomeDataKey"
    }

    override fun getConvenientBanner(): ConvenientBanner<BBInsurance> {
        return convenientBanner
    }

    override fun getComponentContext(): Context {
        return context
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var convertView = inflater?.inflate(getLayoutId(), container, false)!!
        initView(convertView)
        updateBannerList()
        updateView()
        return convertView
    }

    private fun initView(convertView : View) {
        loadingView = convertView.findViewById(R.id.loading_view)
        contentScrollView = convertView.findViewById(R.id.content_scrollview)

        convenientBanner = convertView.findViewById(R.id.convenientBanner)

        compareLayout = convertView.findViewById(R.id.compare_layout)
        consultLayout = convertView.findViewById(R.id.consult_layout)
        evaluateLayout = convertView.findViewById(R.id.evaluate_layout)
        learnLayout = convertView.findViewById(R.id.learn_layout)

        hotCommentLayout = convertView.findViewById(R.id.hot_comment_layout)
        hotCommentHeaderLayout = convertView.findViewById(R.id.hot_comment_header_layout)
        hotCommentAvatarIV = convertView.findViewById(R.id.hot_comment_avatar_iv)
        hotCommentNickNameTV = convertView.findViewById(R.id.hot_comment_nickname_tv)
        hotCommentContentTV = convertView.findViewById(R.id.hot_comment_content_tv)
        hotCommentInfoTV = convertView.findViewById(R.id.hot_comment_info_tv)

        insuranceTypeLayout = convertView.findViewById(R.id.insurance_type_layout)
        insuranceType1IV = convertView.findViewById(R.id.insurance_type_1_iv)
        insuranceType2IV = convertView.findViewById(R.id.insurance_type_2_iv)
        insuranceType3IV = convertView.findViewById(R.id.insurance_type_3_iv)
        insuranceTypeName1TV = convertView.findViewById(R.id.insurance_type_name_1_tv)
        insuranceTypeName2TV = convertView.findViewById(R.id.insurance_type_name_2_tv)
        insuranceTypeName3TV = convertView.findViewById(R.id.insurance_type_name_3_tv)

        companyLayout = convertView.findViewById(R.id.company_layout)
        companyItemContainer = convertView.findViewById(R.id.company_item_container)
        companyHeaderLayout = convertView.findViewById(R.id.company_header_layout)

        evaluateLayout.setOnClickListener({
            var intent = Intent(context, CommentUI::class.java)
            startActivity(intent)
        })
        learnLayout.setOnClickListener({
            var intent = Intent(context, LearnArticleUI::class.java)
            startActivity(intent)
        })
    }

    private fun updateView() {
        if (getHomeDataResponse != null) {
            loadingView.visibility = View.GONE
            contentScrollView.visibility = View.VISIBLE
            updateBannerView()
            updateCommentView()
            updateInsuranceTypeView()
            updateCompanyView()
        } else {
            loadingView.visibility = View.VISIBLE
        }
    }

    private fun updateInsuranceTypeView() {
        if (getHomeDataResponse!!.TopInsuranceTypeList.size > 2) {
            insuranceTypeLayout.visibility = View.VISIBLE
            var insuranceType1 = getHomeDataResponse!!.TopInsuranceTypeList[0]
            var insuranceType2 = getHomeDataResponse!!.TopInsuranceTypeList[1]
            var insuranceType3 = getHomeDataResponse!!.TopInsuranceTypeList[2]
            insuranceType1IV.setImageURI(ProtocolConstants.URL.FileServer + insuranceType1.ThumbUrl)
            insuranceType2IV.setImageURI(ProtocolConstants.URL.FileServer + insuranceType2.ThumbUrl)
            insuranceType3IV.setImageURI(ProtocolConstants.URL.FileServer + insuranceType3.ThumbUrl)
            insuranceTypeName1TV.text = insuranceType1.Name
            insuranceTypeName2TV.text = insuranceType2.Name
            insuranceTypeName3TV.text = insuranceType3.Name
        } else {
            insuranceTypeLayout.visibility = View.GONE
        }
    }

    private fun updateCompanyView() {
        if (getHomeDataResponse!!.TopCompanyList.size > 0) {
            companyLayout.visibility = View.VISIBLE
            companyItemContainer.removeAllViews()
            for (company : BBCompany in getHomeDataResponse!!.TopCompanyList) {
                var itemView = LayoutInflater.from(Application.ApplicationContext).inflate(R.layout.home_company_item_view, null)
                var companyThumbIV = itemView.findViewById<SimpleDraweeView>(R.id.company_iv)
                var companyNameTV = itemView.findViewById<TextView>(R.id.company_name_tv)
                companyNameTV.text = company.Name
                companyThumbIV.setImageURI(ProtocolConstants.URL.FileServer + company.ThumbUrl)
                companyItemContainer.addView(itemView)
                if (companyItemContainer.childCount > 4) {
                    break
                }
            }
        } else {
            companyLayout.visibility = View.GONE
        }
    }

    private fun updateBannerView() {
        if (bannerAdapter == null) {
            bannerAdapter = BannerAdapter(this)
        }
        convenientBanner.setPages(bannerAdapter, getHomeDataResponse?.BannerList)
        convenientBanner.startTurning(2000)
    }

    private fun updateCommentView() {
        if (getHomeDataResponse!!.TopCommentList!!.size > 0) {
            var topComment = getHomeDataResponse!!.TopCommentList[0]
            hotCommentLayout.visibility = View.VISIBLE
            var contactEntity = BBCore.Instance.accountCore.syncService.getContact(topComment.Uin)
            if (contactEntity != null) {
                hotCommentAvatarIV.setImageURI(BBCore.Instance.accountCore.getContactThumbUrl(contactEntity))
                hotCommentNickNameTV.text = contactEntity.Nickname
            } else {
                hotCommentNickNameTV.text = String.format("%d", topComment.Uin)
                hotCommentAvatarIV.setImageURI("res://sadf/" + R.drawable.tab_my_icon)
            }
            hotCommentContentTV.text = topComment.Content
            hotCommentInfoTV.text = getString(R.string.comment_info, topComment.ViewCount, topComment.UpCount)
            hotCommentHeaderLayout.setOnClickListener({
                var intent = Intent(context, CommentUI::class.java)
                startActivity(intent)
            })
        } else {
            hotCommentLayout.visibility = View.GONE
        }
    }

    fun getLayoutId(): Int {
        return R.layout.home_fragment_ui
    }

    private lateinit var loadingView : View
    private lateinit var contentScrollView : View

    private lateinit var compareLayout: View
    private lateinit var consultLayout: View
    private lateinit var evaluateLayout: View
    private lateinit var learnLayout: View
    private lateinit var convenientBanner: ConvenientBanner<BBInsurance>

    private lateinit var hotCommentLayout : View
    private lateinit var hotCommentHeaderLayout : RelativeLayout
    private lateinit var hotCommentAvatarIV: SimpleDraweeView
    private lateinit var hotCommentNickNameTV : TextView
    private lateinit var hotCommentContentTV : TextView
    private lateinit var hotCommentInfoTV: TextView

    private lateinit var insuranceTypeLayout : View
    private lateinit var insuranceType1IV : SimpleDraweeView
    private lateinit var insuranceTypeName1TV : TextView
    private lateinit var insuranceType2IV : SimpleDraweeView
    private lateinit var insuranceTypeName2TV : TextView
    private lateinit var insuranceType3IV : SimpleDraweeView
    private lateinit var insuranceTypeName3TV : TextView

    private lateinit var companyLayout : View
    private lateinit var companyHeaderLayout : View
    private lateinit var companyItemContainer : LinearLayout

    private var bannerAdapter : BannerAdapter ?= null
    private var getHomeDataResponse : BBGetHomeDataResponse ? = null

    private fun updateBannerList() {
        var bannerStr = BBCore.Instance.configCore.storage.getConfigValue(ConfigHomeDataKey, "")
        BBLog.i(TAG, "updateBannerList: %s", bannerStr)
        if (!Util.isNullOrNil(bannerStr)) {
            getHomeDataResponse = JSON.parseObject(bannerStr, BBGetHomeDataResponse::class.java)
        }
        refreshHomeDataList()
    }

    private fun refreshHomeDataList() {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncGetHomeData, ProtocolConstants.URI.DataBin)
        var getHomeDataRequest = BBGetHomeDataRequest()
        netRequest.body = JSON.toJSONString(getHomeDataRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetTaskCancel(netRequest: NetRequest) {
            }

            override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    updateView()
                }
            }

            override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    getHomeDataResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                            BBGetHomeDataResponse::class.java)
                    var value = JSON.toJSONString(getHomeDataResponse)
                    BBCore.Instance.configCore.storage.insertConfig(ConfigHomeDataKey, value)
                }
            }
        })
    }
}