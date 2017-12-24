package com.bbinsurance.android.app.net

import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.lib.BBHandler
import com.bbinsurance.android.lib.log.BBLog
import java.util.concurrent.PriorityBlockingQueue


/**
 * Created by jiaminchen on 2017/10/25.
 */
class NetCore {
    private val TAG = "BB.NetworkCore"

    private var handler : BBHandler
    private var taskQueue : PriorityBlockingQueue<NetTask>

    constructor() {
        var handlerThread = HandlerThread("NetHandlerThread")
        handlerThread.start()
        handler = BBHandler(handlerThread.looper, handlerThread.name)

        taskQueue = PriorityBlockingQueue()
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

    class NetHandler : BBHandler {
        constructor(looper: Looper, name: String) : super(looper, name)

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }
}