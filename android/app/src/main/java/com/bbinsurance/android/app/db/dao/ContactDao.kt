package com.bbinsurance.android.app.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.bbinsurance.android.app.db.entity.ContactEntity;

/**
 * Created by jiaminchen on 17/12/10.
 */
@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(entity :ContactEntity)

    @Query("DELETE FROM Contact WHERE Username=:username")
    fun deleteContact(username : String)

    @Query("SELECT * FROM Contact WHERE Username=:username")
    fun getContact(username : String) : ContactEntity

    @Query("SELECT * FROM Contact WHERE Id=:id")
    fun getContact(id : Long) : ContactEntity
}
