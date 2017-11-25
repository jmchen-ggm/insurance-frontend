package com.bbinsurance.android.app.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jiaminchen on 17/11/22.
 */
@Entity(tableName = "Config")
class ConfigEntity {
    @PrimaryKey
    var Key : String = ""

    @ColumnInfo
    var Value : String = ""
}