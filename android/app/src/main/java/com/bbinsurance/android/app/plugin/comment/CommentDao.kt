package com.bbinsurance.android.app.plugin.comment

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.bbinsurance.android.app.protocol.Comment

/**
 * Created by jiaminchen on 17/11/19.
 */
interface CommentDao {

    @Query("SELECT * FROM Comment")
    fun getAllComment(): List<Comment>

    @Query("SELECT * FROM Comment WHERE Flags & 1 = 0")
    fun getAllUnCreatedComment() : List<Comment>

    @Query("SELECT * FROM Comment WHERE LocalId=:localId")
    fun getCommentByLocalId(localId : Long) : Comment

    @Insert(onConflict = REPLACE)
    fun insertComment(comment: Comment)

    @Update(onConflict = REPLACE)
    fun updateComment(comment: Comment)

    @Delete
    fun deleteComment(comment: Comment)
}