package com.bbinsurance.android.app.plugin.comment.ui

import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.plugin.account.IAccountSyncListener
import com.bbinsurance.android.app.protocol.BBComment
import com.bbinsurance.android.app.protocol.BBListCommentRequest
import com.bbinsurance.android.app.protocol.BBListCommentResponse
import com.bbinsurance.android.app.protocol.BBUpCommentResponse
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.component.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentAdapter : BBBaseAdapter, IAccountSyncListener {

    override fun onAccountSyncSuccess() {
        notifyDataSetChanged()
    }

    override fun onAccountSyncFail() {
        notifyDataSetChanged()
    }

    private var commentList = ArrayList<BBComment>()
    fun refreshCommentList() {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncListComment, ProtocolConstants.URI.DataBin)
        var listCommentRequest = BBListCommentRequest()
        listCommentRequest.StartIndex = commentList.size
        listCommentRequest.PageSize = 20
        netRequest.body = JSON.toJSONString(listCommentRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var listCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                            BBListCommentResponse::class.java)
                    commentList.addAll(listCommentResponse.CommentList)
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

    val TAG = "BB.CommentAdapter"

    constructor(component: ListBaseUIComponent) : super(component) {
        BBCore.Instance.accountCore.syncService.addListener(this)
    }

    override fun getCount(): Int {
        return commentList.size
    }

    override fun createDataItem(position: Int): BaseDataItem {
        var commentDataItem = CommentDataItem(position)
        commentDataItem.comment = commentList[position]
        commentDataItem.upNetListener = upNetListener
        return commentDataItem
    }

    override fun finish() {
        super.finish()
        BBCore.Instance.accountCore.syncService.removeListener(this)
    }

    private var upNetListener = object : NetListener {
        override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
            if (netResponse.respCode == 200) {
                var upCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                        BBUpCommentResponse::class.java)
                for (i in commentList.indices) {
                    if (commentList[i].Id == upCommentResponse.Comment.Id) {
                        commentList[i] = upCommentResponse.Comment
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

}