package com.bbinsurance.android.app.ui.adapter

import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.protocol.BBInsurance
import com.bbinsurance.android.app.protocol.BBListInsuranceRequest
import com.bbinsurance.android.app.protocol.BBListInsuranceResponse
import com.bbinsurance.android.app.ui.component.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.item.InsuranceDataItem

/**
 * Created by jiaminchen on 17/12/20.
 */
class DiscoverAdapter : BBBaseAdapter {

    private var insuranceList = ArrayList<BBInsurance>()
    override fun createDataItem(position: Int): BaseDataItem {
        var index = position
        var entity = insuranceList.get(index)
        var dataItem = InsuranceDataItem(position)
        dataItem.entity = entity
        return dataItem
    }

    override fun getCount(): Int {
        return insuranceList.size
    }

    constructor(uiComponent: ListBaseUIComponent) : super(uiComponent) {
        refreshDiscoverList()
    }

    private fun refreshDiscoverList() {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncListInsurance, ProtocolConstants.URI.DataBin)
        var listInsuranceRequest = BBListInsuranceRequest()
        listInsuranceRequest.StartIndex = 0
        listInsuranceRequest.PageSize = -1
        netRequest.body = JSON.toJSONString(listInsuranceRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetTaskCancel(netRequest: NetRequest) {
            }

            override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var listInsuranceResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                            BBListInsuranceResponse::class.java)
                    insuranceList = listInsuranceResponse.InsuranceList
                    notifyDataSetChanged()
                }
            }

            override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
            }
        })
    }
}