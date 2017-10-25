package com.bbinsurance.android.app.core

import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by jiaminchen on 2017/10/25.
 */
class ThreadCore {
    var threadPool: ThreadPoolExecutor

    constructor() {
        threadPool = ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                SynchronousQueue<Runnable>())
    }

    fun post(runnable: Runnable) {
        threadPool.execute(runnable)
    }
}