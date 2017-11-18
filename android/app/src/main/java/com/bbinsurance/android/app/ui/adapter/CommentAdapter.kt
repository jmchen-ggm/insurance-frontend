package com.bbinsurance.android.app.ui.adapter

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.protocol.BBListCommentResponse
import com.bbinsurance.android.app.protocol.Comment
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.item.CommentDataItem

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentAdapter : BBBaseAdapter {
    val TAG = "BB.CommentAdapter"

    constructor(component: ListBaseUIComponent) : super(component) {
        refreshCommentList();
    }

    private var commentList = ArrayList<Comment>()

    private fun refreshCommentList() {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncListComment, ProtocolConstants.URI.DataBin)
        var requestBody = JSONObject()
        requestBody.put("StartIndex", 0)
        requestBody.put("PageSize", -1)
        netRequest.body = requestBody.toString()
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetDone(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var listCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(), BBListCommentResponse::class.java)
                    commentList = listCommentResponse.CommentList
                    notifyDataSetChanged()
                }
            }
        })
    }

    override fun getCount(): Int {
        return commentList.size
    }

    override fun createDataItem(position: Int): BaseDataItem {
        var commentDataItem = CommentDataItem(position)
        commentDataItem.comment = commentList.get(position)
        return commentDataItem
    }
}