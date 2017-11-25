package com.bbinsurance.android.app.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.bbinsurance.android.app.db.entity.ConfigEntity

/**
 * Created by jiaminchen on 17/11/22.
 */
@Dao
interface ConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfig(entity: ConfigEntity)

    @Query("DELETE FROM Config WHERE key=:key")
    fun deleteConfig(key : String)

    @Query("SELECT * FROM Config WHERE key=:key")
    fun getConfig(key : String) : ConfigEntity
}