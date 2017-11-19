package com.bbinsurance.android.app.core

import android.arch.persistence.room.Room
import com.bbinsurance.android.app.Application
import com.bbinsurance.android.app.db.BBDataBase

/**
 * Created by jiaminchen on 17/11/19.
 */
class DBCore {

    var db : BBDataBase

    constructor() {
        db = Room.databaseBuilder(Application.ApplicationContext, BBDataBase::class.java, "logic.db").allowMainThreadQueries().build()
    }
}