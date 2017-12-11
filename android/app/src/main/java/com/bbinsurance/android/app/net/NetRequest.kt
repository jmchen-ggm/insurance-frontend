package com.bbinsurance.android.app.net

import com.bbinsurance.android.lib.util.TimeUtil
import java.util.*

/**
 * Created by jiaminchen on 2017/10/25.
 */
open class NetRequest {

    var funId : Int = 0
    var uri : String = ""
    var sessionId : String = ""
    var timestamp : Long = 0L
    var body = ""

    constructor(funId : Int, uri : String) {
        this.funId = funId
        this.uri = uri
        this.sessionId = UUID.randomUUID().toString()
        this.timestamp = System.currentTimeMillis()
    }

    fun info() : String {
        return String.format("{funId:%d uri:%s sessionId:%s timestamp:%s}"
            , funId, uri, sessionId, TimeUtil.formatTime(timestamp))
    }

    var isCancel = false
    fun cancel() {
        isCancel = true
    }
}