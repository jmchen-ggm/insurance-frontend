package com.bbinsurance.android.app.plugin.comment.ui

import android.app.Activity
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.plugin.company.ui.CompanySelectListUI
import com.bbinsurance.android.app.plugin.company.ui.InsuranceTypeSelectListUI
import com.bbinsurance.android.app.protocol.BBComment
import com.bbinsurance.android.app.protocol.BBCompany
import com.bbinsurance.android.app.protocol.BBCreateCommentRequest
import com.bbinsurance.android.app.protocol.BBInsuranceType
import com.bbinsurance.android.app.ui.BaseActivity
import com.bbinsurance.android.lib.Util
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 17/11/19.
 */
class AddCommentUI : BaseActivity() {

    companion object {
        val CompanySelectRequestCode = 1
        val InsuranceTypeSelectRequestCode = 2
    }

    lateinit var totalStarsIV: Array<ImageView?>
    lateinit var subStars1TV: Array<ImageView?>
    lateinit var subStars2TV: Array<ImageView?>
    lateinit var subStars3TV: Array<ImageView?>
    lateinit var subStars4TV: Array<ImageView?>

    lateinit var totalStarClickListener: StarIVClickListener
    lateinit var subStar1ClickListner: StarIVClickListener
    lateinit var subStar2ClickListner: StarIVClickListener
    lateinit var subStar3ClickListner: StarIVClickListener
    lateinit var subStar4ClickListner: StarIVClickListener

    lateinit var commentET: EditText
    lateinit var companyThumbIv: SimpleDraweeView
    lateinit var companyNameTV: TextView
    lateinit var insuranceTypeThumbIv: SimpleDraweeView
    lateinit var insuranceTypeNameTV: TextView
    lateinit var selectCompanyTV: TextView
    lateinit var selectInsuranceTypeTV: TextView

    lateinit var errorMsgLayout : View
    lateinit var errorMsgTV : TextView

    var selectCompany: BBCompany? = null
    var selectInsuranceType: BBInsuranceType? = null

    override fun initView() {
        super.initView()
        setBBTitle(R.string.add_comment_title)
        setBackBtn(true, View.OnClickListener { finish() })

        companyThumbIv = findViewById(R.id.company_thumb_iv)
        companyNameTV = findViewById(R.id.company_name_tv)
        insuranceTypeThumbIv = findViewById(R.id.insurance_type_thumb_iv)
        insuranceTypeNameTV = findViewById(R.id.insurance_type_name_tv)
        selectCompanyTV = findViewById(R.id.select_company_tv)
        selectInsuranceTypeTV = findViewById(R.id.select_insurance_type_tv)

        errorMsgLayout = findViewById(R.id.error_msg_layout)
        errorMsgTV = findViewById(R.id.error_msg_tv)

        selectCompanyTV.setOnClickListener({
            var intent = Intent(this, CompanySelectListUI::class.java)
            startActivityForResult(intent, CompanySelectRequestCode)
        })
        selectInsuranceTypeTV.setOnClickListener({
            var intent = Intent(this, InsuranceTypeSelectListUI::class.java)
            startActivityForResult(intent, InsuranceTypeSelectRequestCode)
        })

        setOptionTV(R.string.confirm, View.OnClickListener {
            var content = commentET.text.toString()
            var netRequest = NetRequest(ProtocolConstants.FunId.FuncCreateComment, ProtocolConstants.URI.DataBin)
            var createCommentRequest = BBCreateCommentRequest()
            createCommentRequest.Comment = BBComment()
            createCommentRequest.Comment.Uin = BBCore.Instance.accountCore.loginService.getUIN()
            createCommentRequest.Comment.Content = content.trim()
            createCommentRequest.Comment.TotalScore = totalStarClickListener.score
            if (selectCompany != null) {
                createCommentRequest.Comment.CompanyId = selectCompany!!.Id
            }
            if (selectInsuranceType != null) {
                createCommentRequest.Comment.InsuranceTypeId = selectInsuranceType!!.Id
            }
            createCommentRequest.Comment.Score1 = subStar1ClickListner.score
            createCommentRequest.Comment.Score2 = subStar2ClickListner.score
            createCommentRequest.Comment.Score3 = subStar3ClickListner.score
            createCommentRequest.Comment.Score4 = subStar4ClickListner.score
            createCommentRequest.Comment.Timestamp = System.currentTimeMillis()
            if (checkCommentValid(createCommentRequest.Comment)) {
                netRequest.body = JSON.toJSONString(createCommentRequest)
                BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
                    override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                        finish()
                    }

                    override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
                    }

                    override fun onNetTaskCancel(netRequest: NetRequest) {
                    }
                })
            } else {
                if (Util.isNullOrNil(createCommentRequest.Comment.Content)
                        || createCommentRequest.Comment.Content.length < 15) {
                    showErrorMsg(R.string.comment_error_content_length_too_short)
                } else if (createCommentRequest.Comment.CompanyId == 0L){
                    showErrorMsg(R.string.comment_error_not_select_company)
                } else if (createCommentRequest.Comment.InsuranceTypeId == 0L) {
                    showErrorMsg(R.string.comment_error_not_select_insurance_type)
                }
            }
        })

        totalStarsIV = arrayOfNulls<ImageView?>(5)
        totalStarsIV[0] = findViewById(R.id.star_1_iv)
        totalStarsIV[1] = findViewById(R.id.star_2_iv)
        totalStarsIV[2] = findViewById(R.id.star_3_iv)
        totalStarsIV[3] = findViewById(R.id.star_4_iv)
        totalStarsIV[4] = findViewById(R.id.star_5_iv)
        totalStarClickListener = StarIVClickListener(totalStarsIV, R.drawable.star_gray, R.drawable.star_yellow)
        initIVArrays(totalStarsIV, totalStarClickListener)

        subStars1TV = arrayOfNulls<ImageView?>(5)
        subStars1TV[0] = findViewById(R.id.star_sub_1_1_iv)
        subStars1TV[1] = findViewById(R.id.star_sub_1_2_iv)
        subStars1TV[2] = findViewById(R.id.star_sub_1_3_iv)
        subStars1TV[3] = findViewById(R.id.star_sub_1_4_iv)
        subStars1TV[4] = findViewById(R.id.star_sub_1_5_iv)
        subStar1ClickListner = StarIVClickListener(subStars1TV, R.drawable.sub_star_gray, R.drawable.sub_star_orange)
        initIVArrays(subStars1TV, subStar1ClickListner)

        subStars2TV = arrayOfNulls<ImageView?>(5)
        subStars2TV[0] = findViewById(R.id.star_sub_2_1_iv)
        subStars2TV[1] = findViewById(R.id.star_sub_2_2_iv)
        subStars2TV[2] = findViewById(R.id.star_sub_2_3_iv)
        subStars2TV[3] = findViewById(R.id.star_sub_2_4_iv)
        subStars2TV[4] = findViewById(R.id.star_sub_2_5_iv)
        subStar2ClickListner = StarIVClickListener(subStars2TV, R.drawable.sub_star_gray, R.drawable.sub_star_orange)
        initIVArrays(subStars2TV, subStar2ClickListner)

        subStars3TV = arrayOfNulls<ImageView?>(5)
        subStars3TV[0] = findViewById(R.id.star_sub_3_1_iv)
        subStars3TV[1] = findViewById(R.id.star_sub_3_2_iv)
        subStars3TV[2] = findViewById(R.id.star_sub_3_3_iv)
        subStars3TV[3] = findViewById(R.id.star_sub_3_4_iv)
        subStars3TV[4] = findViewById(R.id.star_sub_3_5_iv)
        subStar3ClickListner = StarIVClickListener(subStars3TV, R.drawable.sub_star_gray, R.drawable.sub_star_orange)
        initIVArrays(subStars3TV, subStar3ClickListner)

        subStars4TV = arrayOfNulls<ImageView?>(5)
        subStars4TV[0] = findViewById(R.id.star_sub_4_1_iv)
        subStars4TV[1] = findViewById(R.id.star_sub_4_2_iv)
        subStars4TV[2] = findViewById(R.id.star_sub_4_3_iv)
        subStars4TV[3] = findViewById(R.id.star_sub_4_4_iv)
        subStars4TV[4] = findViewById(R.id.star_sub_4_5_iv)
        subStar4ClickListner = StarIVClickListener(subStars4TV, R.drawable.sub_star_gray, R.drawable.sub_star_orange)
        initIVArrays(subStars4TV, subStar4ClickListner)

        commentET = findViewById(R.id.comment_et)
    }

    fun initIVArrays(ivs: Array<ImageView?>, listener: View.OnClickListener) {
        for (i in ivs.indices) {
            ivs[i]?.tag = i
            ivs[i]?.setOnClickListener(listener)
        }
    }

    fun showErrorMsg(errorMsgRes : Int) {
        errorMsgTV.setText(errorMsgRes)
        errorMsgLayout.visibility = View.VISIBLE
    }

    class StarIVClickListener : View.OnClickListener {

        var starsIV: Array<ImageView?>
        var initRes: Int
        var clickRes: Int
        var score: Int

        constructor(starsIV: Array<ImageView?>, initRes: Int, clickRes: Int) {
            this.starsIV = starsIV
            this.initRes = initRes
            this.clickRes = clickRes
            this.score = 0
        }

        override fun onClick(view: View?) {
            var index = view?.tag as Int
            score = (index + 1) * 20
            var j = 0
            while (j <= index) {
                this.starsIV[j]?.setImageResource(clickRes)
                j++
            }
            while (j < this.starsIV.size) {
                this.starsIV[j]?.setImageResource(initRes)
                j++
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.add_comment_ui
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CompanySelectRequestCode && resultCode == Activity.RESULT_OK) {
            var selectCompany = JSON.parseObject(data?.getStringExtra(UIConstants.CompanySelectListUI.KeySelectCompany), BBCompany::class.java)
            companyNameTV.tag = selectCompany
            companyNameTV.text = selectCompany.Name
            companyThumbIv.setImageURI(ProtocolConstants.URL.FileServer + selectCompany.ThumbUrl)
            companyNameTV.visibility = View.VISIBLE
            companyThumbIv.visibility = View.VISIBLE
            selectCompanyTV.visibility = View.GONE
        }
        if (requestCode == InsuranceTypeSelectRequestCode && resultCode == Activity.RESULT_OK) {
            var selectInsuranceType = JSON.parseObject(data?.getStringExtra(UIConstants.InsuranceSelectListUI.KeySelectInsuranceType), BBInsuranceType::class.java)
            insuranceTypeNameTV.tag = selectInsuranceType
            insuranceTypeNameTV.text = selectInsuranceType.Name
            insuranceTypeThumbIv.setImageURI(ProtocolConstants.URL.FileServer + selectInsuranceType.ThumbUrl)
            insuranceTypeNameTV.visibility = View.VISIBLE
            insuranceTypeThumbIv.visibility = View.VISIBLE
            selectInsuranceTypeTV.visibility = View.GONE
        }
    }

    private fun checkCommentValid(comment : BBComment) : Boolean {
        if (Util.isNullOrNil(comment.Content)) {
            return false
        }
        if (comment.CompanyId == 0L) {
            return false
        }
        if (comment.InsuranceTypeId == 0L) {
            return false
        }
        return true
    }
}