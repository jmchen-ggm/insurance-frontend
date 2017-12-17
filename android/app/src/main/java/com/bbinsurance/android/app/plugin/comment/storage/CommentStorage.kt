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
class CommentStorage : BBStorage(), CommentDao {
    override fun getCommentByLocalId(localId: Long): CommentEntity {
        return BBCore.Instance.dbCore.db.commentDao().getCommentByLocalId(localId)
    }

    override fun getCommentByServerId(serverId: Long): CommentEntity {
        return BBCore.Instance.dbCore.db.commentDao().getCommentByServerId(serverId)
    }

    fun insertOrUpdateCommentByLocalId(entity : CommentEntity) {
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
        } else {
            insertComment(entity)
        }
    }

    private fun insertOrUpdateCommentByServerId(entity : CommentEntity) {
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
        } else {
            insertComment(entity)
        }
    }

    override fun getAllComment(): Cursor {
        return BBCore.Instance.dbCore.db.commentDao().getAllComment()
    }

    override fun getTopComment(): CommentEntity {
        return BBCore.Instance.dbCore.db.commentDao().getTopComment()
    }

    override fun insertComment(comment: CommentEntity) {
        BBCore.Instance.dbCore.db.commentDao().insertComment(comment)
        notifyMainThread(BBStorageEvent(BBStorageEvent.INSERT))
    }

    override fun getAllUnCreatedComment(): List<CommentEntity> {
        return BBCore.Instance.dbCore.db.commentDao().getAllUnCreatedComment()
    }

    fun updateCommentListByServerId(commentList: List<CommentEntity>) {
        BBCore.Instance.dbCore.db.beginTransaction()
        for (comment: CommentEntity in commentList) {
            insertOrUpdateCommentByServerId(comment)
        }
        BBCore.Instance.dbCore.db.setTransactionSuccessful()
        BBCore.Instance.dbCore.db.endTransaction()
        notifyMainThread(BBStorageEvent(BBStorageEvent.UPDATE))
    }
}