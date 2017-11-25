package com.bbinsurance.android.app.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.bbinsurance.android.app.db.dao.ConfigDao
import com.bbinsurance.android.app.db.dao.CommentDao
import com.bbinsurance.android.app.db.entity.CommentEntity
import com.bbinsurance.android.app.db.entity.ConfigEntity
import com.bbinsurance.android.app.protocol.BBComment

/**
 * Created by jiaminchen on 17/11/19.
 */
@Database(entities = arrayOf(
        CommentEntity::class,
        ConfigEntity::class),
        version = 1, exportSchema = false)
abstract class BBDataBase : RoomDatabase() {
    abstract fun configDao() : ConfigDao
    abstract fun commentDao() : CommentDao
}