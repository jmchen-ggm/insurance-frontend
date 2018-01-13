package com.bbinsurance.android.app.plugin.comment.ui

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
 * Created by jiaminchen on 18/1/13.
 */
class CommentDetailAdapter : BBBaseAdapter {

    var commentReplyList  = ArrayList<BBCommentReply>()

    override fun createDataItem(position: Int): BaseDataItem {
        var dataItem = CommentReplyDataItem(position)
        dataItem.commentReply = commentReplyList[position]
        return dataItem
    }

    override fun getCount(): Int {
        return commentReplyList.size
    }

    constructor(component: ListBaseUIComponent) : super(component) {
    }

    fun refreshCommentReplyList(commentId : Long) {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncGetListCommentReply, ProtocolConstants.URI.DataBin)
        var getListCommentReplyRequest = BBGetListCommentReplyRequest()
        getListCommentReplyRequest.CommentId = commentId
        netRequest.body = JSON.toJSONString(getListCommentReplyRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var getListCommentReplyResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                            BBGetListCommentReplyResponse::class.java)
                    commentReplyList = getListCommentReplyResponse.CommentReplyList

                    clearCache()
                    notifyDataSetChanged()
                }
            }

            override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
            }

            override fun onNetTaskCancel(netRequest: NetRequest) {
            }
        })
    }
}