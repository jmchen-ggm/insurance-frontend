package com.bbinsurance.android.app.net

import android.os.HandlerThread
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.lib.log.BBLog


/**
 * Created by jiaminchen on 2017/10/25.
 */
class NetCore {
    private val TAG = "BB.NetworkCore"

    constructor() {
        var handlerThread = HandlerThread("NetHandlerThread")
        handlerThread.start()
    }

    fun startRequestSync(netRequest: NetRequest) : NetResponse {
        BBLog.i(TAG, "startRequestSync")
        var task = NetTask(netRequest)
        task.run()
        return task.netResponse
    }

    fun startRequestAsync(netRequest: NetRequest) {
        BBLog.i(TAG, "startRequestAsync")
        var task = NetTask(netRequest)
        BBCore.Instance.threadCore.post(task)
    }

    fun startRequestAsync(netRequest: NetRequest, listener: NetListener) {
        BBLog.i(TAG, "startRequestAsync")
        var task = NetTask(netRequest)
        task.netListeners.add(listener)
        BBCore.Instance.threadCore.post(task)
    }

    fun startRequestAsync(netRequest : NetRequest, listeners : List<NetListener>) {
        BBLog.i(TAG, "startRequestAsync")
        var task = NetTask(netRequest)
        task.netListeners.addAll(listeners)
        BBCore.Instance.threadCore.post(task)
    }

    fun cancelRequest(sessionId : String) {

    }
}