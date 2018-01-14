package com.bbinsurance.android.app.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jiaminchen on 18/1/13.
 */
@Entity(tableName = "InsuranceType")
class InsuranceTypeEntity {
    @PrimaryKey
    var Id = 0L
    var Name = ""
    var Desc = ""
    var ThumbUrl = ""
}