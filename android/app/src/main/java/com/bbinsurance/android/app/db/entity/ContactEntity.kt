package com.bbinsurance.android.app.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by jiaminchen on 17/12/10.
 */
@Entity(tableName = "Contact",
        indices = arrayOf(Index(value = "Username")))
class ContactEntity {
    @PrimaryKey
    var Id = 0L
    var Username = ""
    var Nickname = ""
    var PhoneNumber = ""
    var Timestamp = 0L
    var ThumbUrl = ""
}