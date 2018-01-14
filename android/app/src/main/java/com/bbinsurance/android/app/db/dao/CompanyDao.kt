package com.bbinsurance.android.app.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.bbinsurance.android.app.db.entity.CompanyEntity

/**
 * Created by jiaminchen on 18/1/13.
 */
@Dao
interface CompanyDao {
    @Insert
    fun insertCompany(entity : CompanyEntity)

    @Query("SELECT * FROM Company")
    fun getAllCompany() : List<CompanyEntity>
}