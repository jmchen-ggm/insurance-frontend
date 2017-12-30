package com.bbinsurance.android.app.plugin.comment

import android.os.Looper
import android.os.Message
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.CommentEntity
import com.bbinsurance.android.app.db.entity.ConfigEntity
import com.bbinsurance.android.app.net.NetRequest
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

    var commentSyncListenerList = ArrayList<ICommentSyncListener>()

    fun addListener(listener: ICommentSyncListener) {
        commentSyncListenerList.add(listener)
    }

    fun removeListener(listener: ICommentSyncListener) {
        commentSyncListenerList.remove(listener)
    }

    constructor() {
        syncHandler = CommentSyncHandler(Looper.getMainLooper())
    }

    inner class CommentSyncHandler : BBHandler {
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
            var uploadCommentList = BBCore.Instance.commentCore.commentStorage.getAllUnCreatedComment()
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
                    var entity = toCommentEntity(createCommentResponse.Comment)
                    entity.LocalId = comment.LocalId
                    BBCore.Instance.commentCore.commentStorage.insertOrUpdateCommentByLocalId(entity, true)
                }
            }
        }

        private fun toCommentEntity(comment : BBComment) : CommentEntity {
            var entity = CommentEntity()
            entity.Id = comment.Id
            entity.Uin = comment.Uin
            entity.Content = comment.Content
            entity.Timestamp = comment.Timestamp
            entity.Score1 = comment.Score1
            entity.Score2 = comment.Score2
            entity.Score3 = comment.Score3
            entity.TotalScore = comment.TotalScore
            entity.Flags = comment.Flags
            entity.ViewCount = comment.ViewCount
            return entity
        }

        private fun startToSyncCommentList() {
            BBLog.i(TAG, "startToSyncCommentList")
            var netRequest = NetRequest(ProtocolConstants.FunId.FuncListComment, ProtocolConstants.URI.DataBin)
            var listCommentRequest = BBListCommentRequest()
            listCommentRequest.StartIndex = 0
            listCommentRequest.PageSize = 20
            netRequest.body = JSON.toJSONString(listCommentRequest)

            var netResponse = BBCore.Instance.netCore.startRequestSync(netRequest)
            if (netResponse.respCode == 200) {
                var listCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                        BBListCommentResponse::class.java)
                var commentEntityList = ArrayList<CommentEntity>()
                for (comment: BBComment in listCommentResponse.CommentList) {
                    var commentEntity = CommentEntity()
                    commentEntity.Id = comment.Id
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
                    BBLog.i(TAG, "commentEntityList is Empty")
                    return
                }
                updateSequenceRange(commentEntityList)
                BBCore.Instance.commentCore.commentStorage.insertOrUpdateCommentListById(commentEntityList, false)
            }
            BBCore.Instance.uiHandler.post({
                for (listener: ICommentSyncListener in commentSyncListenerList) {
                    listener.onSyncEnd()
                }
            })
        }

        private fun updateSequenceRange(commentEntityList : List<CommentEntity>) {
            var range = CommentRange()
            range.Top = commentEntityList.first().Id
            range.Bottom = commentEntityList.last().Id

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