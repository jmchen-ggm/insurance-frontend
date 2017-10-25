package com.bbinsurance.android.app.net

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import java.util.*

/**
 * Created by jiaminchen on 2017/10/25.
 */
open class NetRequest {

    var funId : Int = 0
    var uri : String = ""
    var sessionId : String = ""
    var timestamp : Long = 0L

    var requestBody : ByteArray = ByteArray(0)

    var url = ""
    var requestMethod = ""

    constructor(funId : Int, uri : String) {
        this.funId = funId
        this.uri = uri
        this.sessionId = UUID.randomUUID().toString()
        this.timestamp = System.currentTimeMillis()
    }

    fun info() : String {
        return String.format("{funId:%d uri:%s sessionId:%s timestamp:%d requestBody:%d url:%s requestMethod:%s}"
            , funId, uri, sessionId, timestamp, requestBody.size, url, requestMethod)
    }

}