package com.bbinsurance.android.app

import android.app.Application
import android.content.Context
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.lib.log.BBLog

/**
 * Created by jiaminchen on 2017/10/23.
 */
class Application : Application() {
    private val TAG = "BB.Application"
    companion object {
        var ApplicationContext : Context ? = null
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ApplicationContext = base
        BBCore.initCore()

        BBLog.i(TAG, "attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()
    }
}