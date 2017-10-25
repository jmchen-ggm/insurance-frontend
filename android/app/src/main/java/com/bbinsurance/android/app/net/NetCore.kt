package com.bbinsurance.android.app.net

import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.lib.log.BBLog
import com.squareup.okhttp.OkHttpClient


/**
 * Created by jiaminchen on 2017/10/25.
 */
class NetCore {
    private val TAG = "BB.NetworkCore"


    constructor() {

    }

    fun startRequestSync(netRequest: NetRequest) : NetResponse {
        BBLog.i(TAG, "startRequestSync")
        var task = NetTask(netRequest)
        task.run()
        return task.netResponse
    }

    fun startRequestAsync(netRequest: NetRequest, listener: NetListener) {
        BBLog.i(TAG, "startRequestAsync")
        var task = NetTask(netRequest)
        task.netListener = listener
        BBCore.Instance.threadCore.post(task)
    }
}