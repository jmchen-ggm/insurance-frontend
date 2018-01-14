package com.bbinsurance.android.app.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.bbinsurance.android.app.db.entity.CompanyEntity
import com.bbinsurance.android.app.db.entity.InsuranceTypeEntity

/**
 * Created by jiaminchen on 18/1/13.
 */
@Dao
interface InsuranceTypeDao {
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertInsuranceType(entity : InsuranceTypeEntity)

    @Query("SELECT * FROM InsuranceType")
    fun getAllInsuranceType() : List<InsuranceTypeEntity>
}