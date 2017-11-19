package com.bbinsurance.android.app.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.bbinsurance.android.app.plugin.comment.CommentDao
import com.bbinsurance.android.app.protocol.Comment

/**
 * Created by jiaminchen on 17/11/19.
 */
@Database(entities = arrayOf(Comment::class), version = 1, exportSchema = false)
open abstract class BBDataBase : RoomDatabase() {
    abstract fun commentDao() : CommentDao
}