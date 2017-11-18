package com.bbinsurance.android.app.ui.adapter

import android.content.Intent
import android.view.View
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.entity.LearnArticleEntity
import com.bbinsurance.android.app.entity.LearnDataResponseEntity
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.item.LearnArticleDataItem
import com.bbinsurance.android.app.ui.webview.BBWebViewUI
import com.bbinsurance.android.lib.log.BBLog

/**
 * Created by jiaminchen on 2017/10/27.
 */
class LearnArticleAdapter : BBBaseAdapter {
    private val TAG = "BB.LearnArticleAdapter"

    constructor(uiComponent: ListBaseUIComponent) : super(uiComponent) {
        refreshLearnArticleList()
    }

    private var learnArticleList : List<LearnArticleEntity> = ArrayList<LearnArticleEntity>()

    override fun createDataItem(position: Int): BaseDataItem {
        var index = position
        var entity = learnArticleList.get(index)
        var dataItem = LearnArticleDataItem(position)
        dataItem.entity = entity
        return dataItem
    }

    private fun refreshLearnArticleList() {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncListArticle, ProtocolConstants.URI.DataBin)
        netRequest.uri = ProtocolConstants.URI.ConfigBin
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetDone(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var responseBodyStr = String(netResponse.responseBody)
                    var learnDataResponseEntity = JSON.parseObject(responseBodyStr, LearnDataResponseEntity::class.java)
                    learnArticleList = learnDataResponseEntity.articles
                    notifyDataSetChanged()
                }
            }
        })
    }

    override fun getCount(): Int {
        return learnArticleList.size
    }

    override fun handleItemClick(view: View?, dataItem: BaseDataItem, isHandle: Boolean) {
        var learnArticleDataItem = dataItem as LearnArticleDataItem
        var intent = Intent(uiComponent.getComponentContext(), BBWebViewUI::class.java)
        intent.putExtra(UIConstants.IntentKey.KeyTitle, learnArticleDataItem.entity.title)
        intent.putExtra(UIConstants.IntentKey.KeyUrl, String.format("http://120.78.175.235:8081/html/articles/%d-content.html", learnArticleDataItem.entity.id))
        uiComponent.getComponentContext().startActivity(intent)
    }
}