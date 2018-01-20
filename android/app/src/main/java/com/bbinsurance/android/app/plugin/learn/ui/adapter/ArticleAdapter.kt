package com.bbinsurance.android.app.plugin.learn.ui.adapter

import android.view.View
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.plugin.learn.ui.item.ArticleDataItem
import com.bbinsurance.android.app.protocol.*
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.component.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 2017/10/27.
 */
class ArticleAdapter : BBBaseAdapter {
    private val TAG = "BB.ArticleAdapter"

    constructor(uiComponent: ListBaseUIComponent) : super(uiComponent) {
        refreshLearnArticleList()
    }

    private var articleList = ArrayList<BBArticle>()

    override fun createDataItem(position: Int): BaseDataItem {
        var index = position
        var entity = articleList.get(index)
        var dataItem = ArticleDataItem(position)
        dataItem.article = entity
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
                    articleList = listArticleResponse.ArticleList
                    notifyDataSetChanged()
                }
            }

            override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
            }
        })
    }

    override fun getCount(): Int {
        return articleList.size
    }

    private fun viewArticle(articleId: Long) {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncViewArticle, ProtocolConstants.URI.DataBin)
        var viewArticleRequest = BBViewArticleRequest()
        viewArticleRequest.Id = articleId
        netRequest.body = JSON.toJSONString(viewArticleRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, viewNetListener)
    }

    private var viewNetListener = object : NetListener {
        override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
            if (netResponse.respCode == 200) {
                var viewArticleResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                        BBViewArticleResponse::class.java)
                for (i in articleList.indices) {
                    if (articleList[i].Id == viewArticleResponse.Article.Id) {
                        articleList[i] = viewArticleResponse.Article
                        break
                    }
                }
                clearCache()
                notifyDataSetChanged()
            }
        }

        override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
        }

        override fun onNetTaskCancel(netRequest: NetRequest) {
        }
    }

    override fun handleItemClick(view: View, dataItem: BaseDataItem, isHandle: Boolean) {
        var articleDataItem = dataItem as ArticleDataItem
        viewArticle(articleDataItem.article.Id)
    }
}