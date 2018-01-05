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
class CompanyAdapter : BBBaseAdapter {

    private var companyList = ArrayList<BBCompany>()
    override fun createDataItem(position: Int): BaseDataItem {
        var companyDataItem = CompanyDataItem(position)
        companyDataItem.company = companyList[position]
        return companyDataItem
    }

    constructor(uiComponent: ListBaseUIComponent) : super(uiComponent)

    fun refreshCompanyList() {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncListCompany, ProtocolConstants.URI.DataBin)
        var listCommentRequest = BBListCompanyRequest()
        listCommentRequest.StartIndex = 0
        listCommentRequest.PageSize = 20
        netRequest.body = JSON.toJSONString(listCommentRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var listCompanyResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                            BBListCompanyResponse::class.java)
                    companyList.addAll(listCompanyResponse.CompanyList)
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
        return companyList.size
    }
}