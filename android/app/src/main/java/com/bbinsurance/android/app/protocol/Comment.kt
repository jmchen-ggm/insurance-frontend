package com.bbinsurance.android.app.protocol

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jiaminchen on 17/11/17.
 */
open class Comment{
    @ColumnInfo(name = "LocalId")
    @PrimaryKey(autoGenerate = true)
    var LocalId = 0L

    @ColumnInfo(name = "ServId")
    var ServId = 0L
    @ColumnInfo(name = "Uin")
    var Uin: Long = 0L
    @ColumnInfo(name = "Content")
    var Content : String = ""
    @ColumnInfo(name = "Score")
    var Score : Int = 0
    @ColumnInfo(name = "Timestamp")
    var Timestamp : Long = 0L
    @ColumnInfo(name = "ViewCount")
    var ViewCount : Int = 0
    @ColumnInfo(name = "Flags")
    var Flags : Long = 0L

}