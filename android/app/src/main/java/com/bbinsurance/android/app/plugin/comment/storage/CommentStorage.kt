package com.bbinsurance.android.app.plugin.comment.storage

import android.database.Cursor
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.dao.CommentDao
import com.bbinsurance.android.app.db.entity.CommentEntity
import com.bbinsurance.android.app.db.storage.BBStorage
import com.bbinsurance.android.app.db.storage.BBStorageEvent

/**
 * Created by jiaminchen on 17/11/19.
 */
class CommentStorage : BBStorage() {

    fun getCommentByLocalId(localId: Long): CommentEntity {
        return BBCore.Instance.dbCore.db.commentDao().getCommentByLocalId(localId)
    }

    fun getCommentByServerId(serverId: Long): CommentEntity {
        return BBCore.Instance.dbCore.db.commentDao().getCommentByServerId(serverId)
    }

    fun insertOrUpdateCommentByLocalId(entity : CommentEntity, needNotify: Boolean) {
        var dbEntity = getCommentByServerId(entity.LocalId)
        if (dbEntity != null) {
            var stat = BBCore.Instance.dbCore.db.compileStatement(
                    "UPDATE `Comment` SET " +
                            "`ServerId` = ?," +
                            "`Uin` = ?," +
                            "`Content` = ?," +
                            "`TotalScore` = ?," +
                            "`Score1` = ?," +
                            "`Score2` = ?," +
                            "`Score3` = ?," +
                            "`Timestamp` = ?," +
                            "`ViewCount` = ?," +
                            "`Flags` = ? " +
                            "WHERE `LocalId` = ?")
            stat.bindLong(1, entity.ServerId)
            stat.bindLong(2, entity.Uin)
            stat.bindString(3, entity.Content)
            stat.bindLong(4, entity.TotalScore.toLong())
            stat.bindLong(5, entity.Score1.toLong())
            stat.bindLong(6, entity.Score2.toLong())
            stat.bindLong(7, entity.Score3.toLong())
            stat.bindLong(8, entity.Timestamp)
            stat.bindLong(9, entity.ViewCount.toLong())
            stat.bindLong(10, entity.Flags)
            stat.bindLong(11, entity.LocalId)
            stat.executeUpdateDelete()
            if (needNotify) {
                notifyMainThread(BBStorageEvent(BBStorageEvent.UPDATE, entity))
            }
        } else {
            insertComment(entity, needNotify)
        }
    }

    private fun insertOrUpdateCommentByServerId(entity : CommentEntity, needNotify: Boolean) {
        var dbEntity = getCommentByServerId(entity.ServerId)
        if (dbEntity != null) {
            var stat = BBCore.Instance.dbCore.db.compileStatement(
                    "UPDATE `Comment` SET " +
                            "`Uin` = ?," +
                            "`Content` = ?," +
                            "`TotalScore` = ?," +
                            "`Score1` = ?," +
                            "`Score2` = ?," +
                            "`Score3` = ?," +
                            "`Timestamp` = ?," +
                            "`ViewCount` = ?," +
                            "`Flags` = ? " +
                            "WHERE `ServerId` = ?")
            stat.bindLong(1, entity.Uin)
            stat.bindString(2, entity.Content)
            stat.bindLong(3, entity.TotalScore.toLong())
            stat.bindLong(4, entity.Score1.toLong())
            stat.bindLong(5, entity.Score2.toLong())
            stat.bindLong(6, entity.Score3.toLong())
            stat.bindLong(7, entity.Timestamp)
            stat.bindLong(8, entity.ViewCount.toLong())
            stat.bindLong(9, entity.Flags)
            stat.bindLong(10, entity.ServerId)
            stat.executeUpdateDelete()
            if (needNotify) {
                notifyMainThread(BBStorageEvent(BBStorageEvent.UPDATE, entity))
            }
        } else {
            insertComment(entity, needNotify)
        }
    }

    fun getAllComment(): Cursor {
        return BBCore.Instance.dbCore.db.commentDao().getAllComment()
    }

    fun getTopComment(): CommentEntity {
        return BBCore.Instance.dbCore.db.commentDao().getTopComment()
    }

    fun insertComment(comment: CommentEntity, needNotify : Boolean) {
        BBCore.Instance.dbCore.db.commentDao().insertComment(comment)
        if (needNotify) {
            notifyMainThread(BBStorageEvent(BBStorageEvent.INSERT, comment))
        }
    }

    fun getAllUnCreatedComment(): List<CommentEntity> {
        return BBCore.Instance.dbCore.db.commentDao().getAllUnCreatedComment()
    }

    fun insertOrUpdateCommentListByServerId(commentList: List<CommentEntity>, needNotify: Boolean) {
        BBCore.Instance.dbCore.db.beginTransaction()
        for (comment: CommentEntity in commentList) {
            insertOrUpdateCommentByServerId(comment, false)
        }
        BBCore.Instance.dbCore.db.setTransactionSuccessful()
        BBCore.Instance.dbCore.db.endTransaction()
        if (needNotify) {
            notifyMainThread(BBStorageEvent(BBStorageEvent.UPDATE))
        }
    }
}