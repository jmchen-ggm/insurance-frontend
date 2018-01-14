package com.bbinsurance.android.app.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.bbinsurance.android.app.db.dao.CompanyDao
import com.bbinsurance.android.app.db.dao.ConfigDao
import com.bbinsurance.android.app.db.dao.ContactDao
import com.bbinsurance.android.app.db.dao.InsuranceTypeDao
import com.bbinsurance.android.app.db.entity.CompanyEntity
import com.bbinsurance.android.app.db.entity.ConfigEntity
import com.bbinsurance.android.app.db.entity.ContactEntity
import com.bbinsurance.android.app.db.entity.InsuranceTypeEntity

/**
 * Created by jiaminchen on 17/11/19.
 */
@Database(entities = arrayOf(
        ConfigEntity::class,
        CompanyEntity::class,
        InsuranceTypeEntity::class,
        ContactEntity::class),
        version = 3, exportSchema = false)
abstract class BBDataBase : RoomDatabase() {
    abstract fun configDao() : ConfigDao
    abstract fun contactDao() : ContactDao
    abstract fun insuranceTypeDao() : InsuranceTypeDao
    abstract fun companyDao() : CompanyDao
}