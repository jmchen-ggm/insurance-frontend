package com.bbinsurance.android.app.plugin.learn.ui.adapter

import android.content.Intent
import android.view.View
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.Application
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.protocol.BBArticle
import com.bbinsurance.android.app.protocol.BBListArticleRequest
import com.bbinsurance.android.app.protocol.BBListArticleResponse
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.plugin.learn.ui.item.ArticleDataItem
import com.bbinsurance.android.app.ui.webview.BBWebViewUI
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams

/**
 * Created by jiaminchen on 2017/10/27.
 */
class ArticleAdapter : BBBaseAdapter {
    private val TAG = "BB.ArticleAdapter"

    constructor(uiComponent: ListBaseUIComponent) : super(uiComponent) {
        refreshLearnArticleList()
    }

    private var learnArticleList = ArrayList<BBArticle>()

    override fun createDataItem(position: Int): BaseDataItem {
        var index = position
        var entity = learnArticleList.get(index)
        var dataItem = ArticleDataItem(position)
        dataItem.entity = entity
        return dataItem
    }

    private fun refreshLearnArticleList() {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncListArticle, ProtocolConstants.URI.DataBin)
        var listArticleRequest = BBListArticleRequest()
        listArticleRequest.StartIndex = 0
        listArticleRequest.PageSize = -1
        netRequest.body = JSON.toJSONString(listArticleRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetTaskCancel(netRequest: NetRequest) {
            }

            override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var listArticleResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                            BBListArticleResponse::class.java)
                    learnArticleList = listArticleResponse.ArticleList
                    notifyDataSetChanged()
                }
            }

            override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
            }
        })
    }

    override fun getCount(): Int {
        return learnArticleList.size
    }

    override fun handleItemClick(view: View?, dataItem: BaseDataItem, isHandle: Boolean) {
        var learnArticleDataItem = dataItem as ArticleDataItem
        var intent = Intent(uiComponent.getComponentContext(), BBWebViewUI::class.java)
        intent.putExtra(UIConstants.IntentKey.KeyTitle, learnArticleDataItem.entity.Title)
        intent.putExtra(UIConstants.IntentKey.KeyUrl, learnArticleDataItem.entity.Url)
        uiComponent.getComponentContext().startActivity(intent)
    }

    companion object {
        fun getCornerRoundHierarchy() : GenericDraweeHierarchy {
            var cornerSize = Application.ApplicationContext.resources.getDimensionPixelSize(R.dimen.ArticleItemCornerRoundSize).toFloat()
            val cornerRoundParams = RoundingParams()
            cornerRoundParams.setCornersRadii(cornerSize, cornerSize, 0f, 0f)
            val articleHierarchy = GenericDraweeHierarchyBuilder.newInstance(Application.ApplicationContext.resources)
                    .setRoundingParams(cornerRoundParams)
                    //构建
                    .build()
            return articleHierarchy
        }
    }
}