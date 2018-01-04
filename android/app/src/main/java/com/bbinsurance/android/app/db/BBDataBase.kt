package com.bbinsurance.android.app.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.bbinsurance.android.app.db.dao.ConfigDao
import com.bbinsurance.android.app.db.dao.ContactDao
import com.bbinsurance.android.app.db.entity.ConfigEntity
import com.bbinsurance.android.app.db.entity.ContactEntity

/**
 * Created by jiaminchen on 17/11/19.
 */
@Database(entities = arrayOf(
        ConfigEntity::class,
        ContactEntity::class),
        version = 2, exportSchema = false)
abstract class BBDataBase : RoomDatabase() {
    abstract fun configDao() : ConfigDao
    abstract fun contactDao() : ContactDao
}