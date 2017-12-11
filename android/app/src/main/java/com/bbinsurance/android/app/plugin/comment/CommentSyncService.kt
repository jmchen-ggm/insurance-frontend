package com.bbinsurance.android.app.plugin.comment

import android.os.Looper
import android.os.Message
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.CommentEntity
import com.bbinsurance.android.app.db.entity.ConfigEntity
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.plugin.comment.config.CommentRange
import com.bbinsurance.android.app.protocol.*
import com.bbinsurance.android.lib.BBHandler
import com.bbinsurance.android.lib.log.BBLog
import java.util.*

/**
 * Created by jiaminchen on 17/11/19.
 */
class CommentSyncService {
    val TAG = "BB.CommentSyncService";
    var syncHandler : BBHandler

    companion object {
        private val ThreadName = "CommentSyncService"

        private val UploadComment = 1;
        private val SyncComment = 2;
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
                SyncComment -> {
                    BBCore.Instance.threadCore.post(Runnable { startToSyncCommentList() })
                }
            }
        }

        private fun startToUploadComment() {
            var uploadCommentList = BBCore.Instance.dbCore.db.commentDao().getAllUnCreatedComment()
            BBLog.i(TAG, "uploadCommentList: %d", uploadCommentList.size)
            for (comment : CommentEntity in uploadCommentList) {
                var netRequest = NetRequest(ProtocolConstants.FunId.FuncCreateComment, ProtocolConstants.URI.DataBin)
                var createCommentRequest = BBCreateCommentRequest()
                createCommentRequest.Comment = BBComment()
                createCommentRequest.Comment.Uin = comment.Uin
                createCommentRequest.Comment.Content = comment.Content
                createCommentRequest.Comment.TotalScore = comment.TotalScore
                createCommentRequest.Comment.Score1 = comment.Score1
                createCommentRequest.Comment.Score2 = comment.Score2
                createCommentRequest.Comment.Score3 = comment.Score3
                createCommentRequest.Comment.Timestamp = comment.Timestamp
                createCommentRequest.Comment.ViewCount = comment.ViewCount
                createCommentRequest.Comment.Flags = comment.Flags
                netRequest.body = JSON.toJSONString(createCommentRequest)
                var netResponse = BBCore.Instance.netCore.startRequestSync(netRequest)
                if (netResponse.respCode == 200) {
                    var createCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(), BBCreateCommentResponse::class.java)
                    comment.ServerId = createCommentResponse.ServerId
                    comment.Flags = comment.Flags or ProtocolConstants.CommentFlag.CREATED
                    BBCore.Instance.commentCore.commentStorage.updateComment(comment)
                }
            }
        }

        private fun startToSyncCommentList() {
            BBLog.i(TAG, "startToSyncCommentList")
            var netRequest = NetRequest(ProtocolConstants.FunId.FuncListComment, ProtocolConstants.URI.DataBin)
            var listCommentRequest = BBListCommentRequest()
            listCommentRequest.StartIndex = 0
            listCommentRequest.PageSize = 20
            netRequest.body = JSON.toJSONString(listCommentRequest)
            BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
                override fun onNetTaskCancel(netRequest: NetRequest) {
                }

                lateinit var listCommentResponse : BBListCommentResponse
                override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                }

                override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
                    if (netResponse.respCode == 200) {
                        listCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                                BBListCommentResponse::class.java)
                        var commentEntityList = ArrayList<CommentEntity>()
                        for (comment : BBComment in listCommentResponse.CommentList) {
                            var commentEntity = CommentEntity()
                            commentEntity.ServerId = comment.ServerId
                            commentEntity.Content = comment.Content
                            commentEntity.TotalScore = comment.TotalScore
                            commentEntity.Score1 = comment.Score1
                            commentEntity.Score2 = comment.Score2
                            commentEntity.Score3 = comment.Score3
                            commentEntity.Uin = comment.Uin
                            commentEntity.Timestamp = comment.Timestamp
                            commentEntity.Flags = comment.Flags
                            commentEntity.ViewCount = comment.ViewCount
                            commentEntityList.add(commentEntity)
                        }
                        if (commentEntityList.isEmpty()) {
                            BBLog.i(TAG,"commentEntityList is Empty")
                            return
                        }

                        updateSequenceRange(commentEntityList)

                        BBCore.Instance.commentCore.commentStorage.updateCommentListByServerId(commentEntityList)
                    }
                }
            })
        }

        fun updateSequenceRange(commentEntityList : List<CommentEntity>) {
            var range = CommentRange()
            range.Top = commentEntityList.first().ServerId
            range.Bottom = commentEntityList.last().ServerId

            var commentRangeListStr = BBCore.Instance.configCore.storage.getConfigValue(CommentConstants.CommentRankListObjKey, "[]")
            var commentRangeList = JSON.parseArray(commentRangeListStr, CommentRange::class.java)
            commentRangeList.add(range)
            Collections.sort(commentRangeList)
            var newCommentRankList = ArrayList<CommentRange>()

            for (index in commentRangeList.indices) {
                var currentRange = commentRangeList.get(index)
                if (index + 1 < commentRangeList.size) {
                    var nextRange = commentRangeList.get(index + 1)
                    if (nextRange.merge(currentRange)) {
                        continue
                    } else {
                        newCommentRankList.add(currentRange)
                    }
                } else {
                    newCommentRankList.add(currentRange)
                }
            }

            var configEntity = ConfigEntity()
            configEntity.Key = CommentConstants.CommentRankListObjKey
            configEntity.Value = JSON.toJSONString(newCommentRankList)
            BBLog.i(TAG, "SaveConfig: %s %s", configEntity.Key, configEntity.Value)
            BBCore.Instance.configCore.storage.insertConfig(configEntity)
        }
    }

    fun startToUpload() {
        syncHandler.sendMessageDelayed(syncHandler.obtainMessage(UploadComment), 100)
    }

    fun startToSyncCommentList() {
        syncHandler.sendMessageDelayed(syncHandler.obtainMessage(SyncComment), 100)
    }
}