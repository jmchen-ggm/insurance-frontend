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
    override fun getAllComment(): Cursor {
        return BBCore.Instance.dbCore.db.commentDao().getAllComment()
    }

    override fun deleteComment(comment: CommentEntity) {
        BBCore.Instance.dbCore.db.commentDao().deleteComment(comment)
        notifyMainThread(BBStorageEvent(BBStorageEvent.DELETE))
    }

    override fun getTopComment(): CommentEntity {
        return BBCore.Instance.dbCore.db.commentDao().getTopComment()
    }

    override fun insertComment(comment: CommentEntity) {
        BBCore.Instance.dbCore.db.commentDao().insertComment(comment)
        notifyMainThread(BBStorageEvent(BBStorageEvent.INSERT))
    }

    override fun updateComment(comment: CommentEntity) {
        BBCore.Instance.dbCore.db.commentDao().updateComment(comment)
        notifyMainThread(BBStorageEvent(BBStorageEvent.UPDATE))
    }

    override fun getCommentListLimit(limit: Int, offset: Int): List<CommentEntity> {
        return BBCore.Instance.dbCore.db.commentDao().getCommentListLimit(limit, offset)
    }

    override fun getAllUnCreatedComment(): List<CommentEntity> {
        return BBCore.Instance.dbCore.db.commentDao().getAllUnCreatedComment()
    }

    fun updateCommentListByServerId(commentList: List<CommentEntity>) {
        var stat = BBCore.Instance.dbCore.db.compileStatement(
                "UPDATE OR REPLACE `Comment` SET " +
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
        BBCore.Instance.dbCore.db.beginTransaction()
        for (comment: CommentEntity in commentList) {
            stat.bindLong(1, comment.Uin)
            stat.bindString(2, comment.Content)
            stat.bindLong(3, comment.TotalScore.toLong())
            stat.bindLong(4, comment.Score1.toLong())
            stat.bindLong(5, comment.Score2.toLong())
            stat.bindLong(6, comment.Score3.toLong())
            stat.bindLong(7, comment.Timestamp)
            stat.bindLong(8, comment.ViewCount.toLong())
            stat.bindLong(9, comment.Flags)
            stat.bindLong(10, comment.ServerId)
            stat.execute()
        }
        BBCore.Instance.dbCore.db.setTransactionSuccessful()
        BBCore.Instance.dbCore.db.endTransaction()
        notifyMainThread(BBStorageEvent(BBStorageEvent.UPDATE))
    }
}