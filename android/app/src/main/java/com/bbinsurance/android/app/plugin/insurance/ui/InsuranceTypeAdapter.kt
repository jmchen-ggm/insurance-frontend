package com.bbinsurance.android.app.plugin.company.ui

import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.protocol.*
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.component.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 18/1/6.
 */
class InsuranceTypeAdapter : BBBaseAdapter {

    private var insuranceTypeList = ArrayList<BBInsuranceType>()
    override fun createDataItem(position: Int): BaseDataItem {
        var companyDataItem = InsuranceTypeDataItem(position)
        companyDataItem.insuranceType = insuranceTypeList[position]
        return companyDataItem
    }

    constructor(uiComponent: ListBaseUIComponent) : super(uiComponent)

    fun refreshCompanyList() {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncListInsuranceType, ProtocolConstants.URI.DataBin)
        var listCommentRequest = BBListCompanyRequest()
        listCommentRequest.StartIndex = 0
        listCommentRequest.PageSize = 20
        netRequest.body = JSON.toJSONString(listCommentRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var listInsuranceTypeResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                            BBListInsuranceTypeResponse::class.java)
                    insuranceTypeList.addAll(listInsuranceTypeResponse.InsuranceTypeList)
                    uiComponent.onLoadMoreFinish()
                    notifyDataSetChanged()
                }
            }

            override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
            }

            override fun onNetTaskCancel(netRequest: NetRequest) {
            }
        })
    }

    override fun getCount(): Int {
        return insuranceTypeList.size
    }
}