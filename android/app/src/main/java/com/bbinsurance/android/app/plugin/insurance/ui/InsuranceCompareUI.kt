package com.bbinsurance.android.app.plugin.insurance.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Button
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
class InsuranceCompareUI : BaseActivity() {

    private lateinit var expandLv: ExpandableListView
    private lateinit var compareBtn : Button

    private lateinit var leftInsuranceLayout : View
    private lateinit var rightInsuranceLayout : View
    private lateinit var companyLeftThumbIV : SimpleDraweeView
    private lateinit var insuranceLeftNameTV : TextView
    private lateinit var companyRightThumbIV : SimpleDraweeView
    private lateinit var insuranceRightNameTV : TextView

    private var leftDetail: BBInsuranceDetail? = null
    private var rightDetail: BBInsuranceDetail? = null

    private lateinit var compareAdapter: InsuranceCompareAdapter

    private fun requestDetailData(id: Long, netListener: NetListener) {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncGetInsuranceDetail, ProtocolConstants.URI.DataBin)
        var getInsuranceDetailRequest = BBGetInsuranceDetailRequest()
        getInsuranceDetailRequest.Id = id
        netRequest.body = JSON.toJSONString(getInsuranceDetailRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, netListener)
    }

    private var leftDetailInsuranceNetListener = object : NetListener {
        override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
            if (netResponse.respCode == 200) {
                var getInsuranceDetailResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                        BBGetInsuranceDetailResponse::class.java)
                leftDetail = getInsuranceDetailResponse.InsuranceDetail
                updateLeftInsurance()
                updateCompareBtn()
            }
        }

        override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
        }

        override fun onNetTaskCancel(netRequest: NetRequest) {
        }
    }

    private var rightDetailInsuranceNetListener = object : NetListener {
        override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
            if (netResponse.respCode == 200) {
                var getInsuranceDetailResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                        BBGetInsuranceDetailResponse::class.java)
                rightDetail = getInsuranceDetailResponse.InsuranceDetail
                updateRightInsurance()
                updateCompareBtn()
            }
        }

        override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
        }

        override fun onNetTaskCancel(netRequest: NetRequest) {
        }
    }

    fun updateLeftInsurance() {
        if (leftDetail == null) {
            return
        }
        companyLeftThumbIV.hierarchy = InsuranceAdapter.getCornerRoundHierarchy()
        companyLeftThumbIV.setImageURI(leftDetail?.Company?.ThumbUrl)
        insuranceLeftNameTV.text = leftDetail?.Name
    }

    fun updateRightInsurance() {
        if (rightDetail == null) {
            return
        }
        companyRightThumbIV.hierarchy = InsuranceAdapter.getCornerRoundHierarchy()
        companyRightThumbIV.setImageURI(rightDetail?.Company?.ThumbUrl)
        insuranceRightNameTV.text = rightDetail?.Name
    }

    fun updateCompareBtn() {
        compareBtn.isEnabled = rightDetail != null && leftDetail != null
    }

    override fun initView() {
        super.initView()

        setBBTitle(R.string.insurance_compare)
        setBackBtn(true, View.OnClickListener {
            finish()
        })

        expandLv = findViewById(R.id.expand_lv)
        compareAdapter = InsuranceCompareAdapter(this)

        compareBtn = findViewById(R.id.compare_btn)

        leftInsuranceLayout = findViewById(R.id.left_insurance_layout)
        rightInsuranceLayout = findViewById(R.id.right_insurance_layout)

        companyLeftThumbIV = findViewById(R.id.company_left_thumb_iv)
        insuranceLeftNameTV = findViewById(R.id.insurance_left_name_tv)
        companyRightThumbIV = findViewById(R.id.company_right_thumb_iv)
        insuranceRightNameTV = findViewById(R.id.insurance_right_name_tv)

        expandLv.setAdapter(compareAdapter)

        leftInsuranceLayout.setOnClickListener({
            var intent = Intent(this, InsuranceSelectUI::class.java)
            startActivityForResult(intent, UIConstants.GlobalRequestCode.SelectLeftInsuranceRequestCode)
        })

        rightInsuranceLayout.setOnClickListener({
            var intent = Intent(this, InsuranceSelectUI::class.java)
            startActivityForResult(intent, UIConstants.GlobalRequestCode.SelectRightInsuranceRequestCode)
        })

        compareBtn.isEnabled = false
        compareBtn.setOnClickListener({
            compareAdapter.setData(leftDetail!!, rightDetail!!)
            expandLv.expandGroup(0)
            expandLv.expandGroup(1)
            expandLv.expandGroup(2)
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.insurance_compare_ui
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == UIConstants.GlobalRequestCode.SelectLeftInsuranceRequestCode) {
                var id = data!!.getLongExtra(UIConstants.InsuranceSelectUI.KeyInsuranceId, -1)
                requestDetailData(id, leftDetailInsuranceNetListener)
            } else if (requestCode == UIConstants.GlobalRequestCode.SelectRightInsuranceRequestCode) {
                var id = data!!.getLongExtra(UIConstants.InsuranceSelectUI.KeyInsuranceId, -1)
                requestDetailData(id, rightDetailInsuranceNetListener)
            }
            compareAdapter.clearData()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}