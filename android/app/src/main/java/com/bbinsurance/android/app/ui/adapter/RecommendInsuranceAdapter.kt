package com.bbinsurance.android.app.ui.adapter

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.entity.DataResponseEntity
import com.bbinsurance.android.app.entity.InsuranceEntity
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.item.RecommendInsuranceDataItem
import com.bbinsurance.android.lib.log.BBLog

/**
 * Created by jiaminchen on 2017/10/25.
 */
class RecommendInsuranceAdapter : BBBaseAdapter{
    private val TAG = "BB.RecommendInsuranceAdapter"
    private var insuranceList : List<InsuranceEntity> = ArrayList<InsuranceEntity>()

    constructor(uiComponent: ListBaseUIComponent) : super(uiComponent) {

    }

    override fun getCount(): Int {
        if (insuranceList == null) {
            return 0
        } else {
            return insuranceList.size
        }
    }

    override fun createDataItem(position: Int): BaseDataItem {
        var index = position
        var entity = insuranceList.get(index)
        var dataItem = RecommendInsuranceDataItem(position)
        dataItem.insuranceEntity = entity
        return dataItem
    }

    fun refreshRecommendInsuranceList() {
        var netRequest = NetRequest(ProtocolConstants.FunId.RecommendationInsurance, ProtocolConstants.URI.DataBin)
        var requestBody = JSONObject()
        requestBody.put("start", 0)
        requestBody.put("end", 10)
        netRequest.requestBody = requestBody.toString().toByteArray()
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetDone(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var responseBodyStr = String(netResponse.responseBody)
                    BBLog.i(TAG, "responseBodyStr: %s", responseBodyStr)
                    var dataResponseEntity = JSON.parseObject(responseBodyStr, DataResponseEntity::class.java)
                    insuranceList = dataResponseEntity.element
                    notifyDataSetChanged()
                }
            }
        })
    }
}