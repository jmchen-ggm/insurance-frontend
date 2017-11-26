package com.bbinsurance.android.app.db.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.database.Cursor
import com.bbinsurance.android.app.db.entity.CommentEntity

/**
 * Created by jiaminchen on 17/11/19.
 */
@Dao
interface CommentDao {

    @Query("SELECT * FROM Comment ORDER BY Timestamp DESC LIMIT :limit OFFSET :offset")
    fun getCommentListLimit(limit: Int, offset: Int) : List<CommentEntity>

    @Query("SELECT * FROM Comment WHERE Flags & 1 = 0 ORDER BY Timestamp DESC;")
    fun getAllUnCreatedComment(): List<CommentEntity>

    @Query("SELECT * FROM Comment ORDER BY Timestamp DESC LIMIT 1")
    fun getTopComment() : CommentEntity

    @Query("SELECT * FROM Comment ORDER BY Timestamp DESC")
    fun getAllComment() : Cursor

    @Insert(onConflict = REPLACE)
    fun insertComment(comment: CommentEntity)

    @Update(onConflict = REPLACE)
    fun updateComment(comment: CommentEntity)

    @Delete
    fun deleteComment(comment: CommentEntity)
}