package com.bbinsurance.android.lib

import android.os.Handler
import android.os.Looper
import com.bbinsurance.android.lib.log.BBLog

/**
 * Created by jiaminchen on 2017/10/25.
 */
open class BBHandler : Handler{
    val TAG = "BB.BBHandler";
    var name : String
    constructor(looper : Looper, name : String) : super(looper) {
        this.name = name
        BBLog.i(TAG, "Create BBHandler %s", name)
    }

    fun quit() {
        BBLog.i(TAG, "Quit BBHandler %s", name)
        looper.quit()
    }
}