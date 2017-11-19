package com.bbinsurance.android.app.plugin.comment

import android.os.Looper
import android.os.Message
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.protocol.BBCreateCommentRequest
import com.bbinsurance.android.app.protocol.BBCreateCommentResponse
import com.bbinsurance.android.app.protocol.Comment
import com.bbinsurance.android.lib.BBHandler

/**
 * Created by jiaminchen on 17/11/19.
 */
class CommentSyncService {

    var syncHandler : BBHandler

    companion object {
        private val ThreadName = "CommentSyncService"

        private val UploadComment = 1;
    }

    constructor() {
        syncHandler = CommentSyncHandler(Looper.getMainLooper())
    }

    class CommentSyncHandler : BBHandler {
        constructor(looper : Looper) : super(looper, ThreadName) {
        }

        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                UploadComment -> {
                    BBCore.Instance.threadCore.post(Runnable { startToUploadComment() })
                }
            }
        }

        private fun startToUploadComment() {
            var uploadCommentList = BBCore.Instance.dbCore.db.commentDao().getAllUnCreatedComment()
            for (comment : Comment in uploadCommentList) {
                var netRequest = NetRequest(ProtocolConstants.FunId.FuncCreateComment, ProtocolConstants.URI.DataBin)
                var createCommentRequest = BBCreateCommentRequest()
                createCommentRequest.Comment = comment
                netRequest.body = JSON.toJSONString(createCommentRequest)
                var netResponse = BBCore.Instance.netCore.startRequestSync(netRequest)
                if (netResponse.respCode == 200) {
                    var createCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(), BBCreateCommentResponse::class.java)
                    comment.ServId = createCommentResponse.ServId
                    BBCore.Instance.dbCore.db.commentDao().updateComment(comment)
                }
            }
        }
    }

    fun startToUpload() {
        syncHandler.sendMessageDelayed(syncHandler.obtainMessage(1), 100)
    }
}