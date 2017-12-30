package com.bbinsurance.android.app.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.util.TableInfo

/**
 * Created by jiaminchen on 17/11/21.
 */
@Entity(tableName = "Comment",
        indices = arrayOf(Index(value = "Timestamp"), Index(value = "Id")))
open class CommentEntity {
    @PrimaryKey()
    @ColumnInfo()
    var LocalId = 0L
    @ColumnInfo()
    var Id = 0L
    @ColumnInfo()
    var Uin: Long = 0L
    @ColumnInfo()
    var Content: String = ""
    @ColumnInfo()
    var TotalScore: Int = 0
    @ColumnInfo()
    var Score1: Int = 0
    @ColumnInfo()
    var Score2: Int = 0
    @ColumnInfo()
    var Score3: Int = 0
    @ColumnInfo()
    var Timestamp: Long = 0L
    @ColumnInfo()
    var ViewCount: Int = 0
    @ColumnInfo()
    var Flags: Long = 0L

}