package com.bbinsurance.android.app.plugin.insurance.ui;

import android.view.View
import android.widget.ExpandableListView
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.protocol.BBGetInsuranceDetailRequest
import com.bbinsurance.android.app.protocol.BBGetInsuranceDetailResponse
import com.bbinsurance.android.app.protocol.BBInsuranceDetail
import com.bbinsurance.android.app.ui.BaseActivity
import com.bbinsurance.android.app.ui.adapter.InsuranceAdapter
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 18/2/3.
 */

class InsuranceDetailUI : BaseActivity() {

    private val TAG = "BB.InsuranceDetailUI"

    private lateinit var expandLv : ExpandableListView
    private lateinit var loadingView : View
    private lateinit var insuranceDetail : BBInsuranceDetail

    private lateinit var insuranceDetailAdapter : InsuranceDetailAdapter

    override fun initData() {
        super.initData()
        var id = intent.getLongExtra(UIConstants.InsuranceDetailUI.KeyInsuranceId, 0L)
        requestDetailData(id)
    }

    private fun requestDetailData(id : Long) {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncGetInsuranceDetail, ProtocolConstants.URI.DataBin)
        var getInsuranceDetailRequest = BBGetInsuranceDetailRequest()
        getInsuranceDetailRequest.Id = id
        netRequest.body = JSON.toJSONString(getInsuranceDetailRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, getDetailInsuranceNetListener)
    }

    private var getDetailInsuranceNetListener = object : NetListener {
        override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
            if (netResponse.respCode == 200) {
                var getInsuranceDetailResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                        BBGetInsuranceDetailResponse::class.java)
                insuranceDetail = getInsuranceDetailResponse.InsuranceDetail
                updateHeaderView()
                expandLv.visibility = View.VISIBLE
                loadingView.visibility = View.GONE
                insuranceDetailAdapter.setData(JSON.parseObject(insuranceDetail.DetailData))
            }
        }

        override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
        }

        override fun onNetTaskCancel(netRequest: NetRequest) {
        }
    }

    override fun initView() {
        super.initView()

        setBBTitle(R.string.insurance_detail)
        setBackBtn(true, View.OnClickListener {
            finish()
        })

        loadingView = findViewById(R.id.loading_view)
        expandLv = findViewById(R.id.expand_lv)
        insuranceDetailAdapter = InsuranceDetailAdapter(this)

        var headView = getHeaderView()
        if (headView != null) {
            expandLv.addHeaderView(headView)
        }
        expandLv.setAdapter(insuranceDetailAdapter)
    }

    private lateinit var thumbIV : SimpleDraweeView
    private lateinit var nameTV : TextView
    private lateinit var ageTV : TextView
    private lateinit var compensationTV : TextView
    private lateinit var premiumTV : TextView
    private lateinit var companyThumbIV : SimpleDraweeView
    private lateinit var companyNameTV : TextView
    private lateinit var insuranceTypeThumbIV : SimpleDraweeView
    private lateinit var insuranceTypeNameTV : TextView

    private fun getHeaderView() : View {
        var header = layoutInflater.inflate(R.layout.insurance_detail_header, null)
        thumbIV = header.findViewById(R.id.thumb_iv)
        ageTV = header.findViewById(R.id.age_tv)
        nameTV = header.findViewById(R.id.name_tv)
        premiumTV = header.findViewById(R.id.premium_tv)
        compensationTV = header.findViewById(R.id.compensation_tv)
        companyThumbIV = header.findViewById(R.id.company_thumb_iv)
        companyNameTV = header.findViewById(R.id.company_name_tv)
        insuranceTypeThumbIV = header.findViewById(R.id.insurance_type_thumb_iv)
        insuranceTypeNameTV = header.findViewById(R.id.insurance_type_name_tv)
        return header
    }

    private fun updateHeaderView() {
        thumbIV.hierarchy = InsuranceAdapter.getCornerRoundHierarchy()
        thumbIV.setImageURI(insuranceDetail.ThumbUrl)
        nameTV.text = insuranceDetail.Name
        ageTV.text = getString(R.string.insurance_age_range, insuranceDetail.AgeFrom, insuranceDetail.AgeTo)
        premiumTV.text = getString(R.string.annal_premium, insuranceDetail.AnnualPremium)
        compensationTV.text = getString(R.string.annal_compensation, insuranceDetail.AnnualCompensation / 10000)
        companyThumbIV.hierarchy = InsuranceAdapter.getCornerRoundHierarchy()
        companyThumbIV.setImageURI(insuranceDetail.Company.ThumbUrl)
        companyNameTV.text = insuranceDetail.Company.Name
        insuranceTypeThumbIV.hierarchy = InsuranceAdapter.getCornerRoundHierarchy()
        insuranceTypeThumbIV.setImageURI(insuranceDetail.InsuranceType.ThumbUrl)
        insuranceTypeNameTV.text = insuranceDetail.InsuranceType.Name
    }

    override fun getLayoutId(): Int {
        return R.layout.insurance_detail_ui;
    }
}
