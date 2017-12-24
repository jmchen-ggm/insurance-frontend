package com.bbinsurance.android.app.ui.adapter

import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.Application
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
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
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams

/**
 * Created by jiaminchen on 2017/10/25.
 */
class InsuranceAdapter : BBBaseAdapter{
    private val TAG = "BB.InsuranceAdapter"
    private var insuranceList = ArrayList<BBInsurance>()

    constructor(uiComponent: ListBaseUIComponent) : super(uiComponent) {
        refreshRecommendInsuranceList()
    }

    override fun getCount(): Int {
        return insuranceList.size
    }

    override fun createDataItem(position: Int): BaseDataItem {
        var index = position
        var entity = insuranceList.get(index)
        var dataItem = InsuranceDataItem(position)
        dataItem.entity = entity
        return dataItem
    }

    private fun refreshRecommendInsuranceList() {
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

    companion object {
        fun getCornerRoundHierarchy() : GenericDraweeHierarchy {
            var cornerSize = Application.ApplicationContext.resources.getDimensionPixelSize(R.dimen.InsuranceItemCornerRoundSize).toFloat()
            val cornerRoundParams = RoundingParams()
            cornerRoundParams.setCornersRadii(cornerSize, cornerSize, cornerSize, cornerSize)
            val articleHierarchy = GenericDraweeHierarchyBuilder.newInstance(Application.ApplicationContext.resources)
                    .setRoundingParams(cornerRoundParams)
                    //构建
                    .build()
            return articleHierarchy
        }
    }
}