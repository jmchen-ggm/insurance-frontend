package com.bbinsurance.android.app.protocol

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jiaminchen on 17/11/17.
 */
open class BBComment {
    var ServerId = 0L
    var Uin: Long = 0L
    var Content : String = ""
    var TotalScore : Int = 0
    var Score1 : Int = 0
    var Score2 : Int = 0
    var Score3 : Int = 0
    var Timestamp : Long = 0L
    var ViewCount : Int = 0
    var Flags : Long = 0L
}