package com.bbinsurance.android.app

import android.app.Application
import android.content.Context
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.lib.log.BBLog
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by jiaminchen on 2017/10/23.
 */
class Application : Application() {
    private val TAG = "BB.Application"
    companion object {
        lateinit var ApplicationContext : Context
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ApplicationContext = base!!
        BBCore.initCore()

        BBLog.i(TAG, "attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        FLog.setMinimumLoggingLevel(FLog.DEBUG)
    }
}