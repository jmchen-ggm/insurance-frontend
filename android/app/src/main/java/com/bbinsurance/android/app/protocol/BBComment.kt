package com.bbinsurance.android.app.protocol

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jiaminchen on 17/11/17.
 */
open class BBComment {
    var Id = 0L
    var Uin: Long = 0L
    var Content : String = ""
    var TotalScore : Int = 0
    var CompanyId : Long = 0
    var CompanyName : String = ""
    var InsuranceTypeId : Long = 0
    var InsuranceTypeName : String = ""
    var Score1 : Int = 0
    var Score2 : Int = 0
    var Score3 : Int = 0
    var Score4 : Int = 0
    var Timestamp : Long = 0L
    var UpCount : Int = 0
    var ViewCount : Int = 0
    var ReplyCount : Int = 0
    var Flags : Long = 0L
    var IsUp : Boolean = false
}