package com.bbinsurance.android.app

import android.app.Application
import android.content.Context
import com.bbinsurance.android.lib.Constants
import com.bbinsurance.android.lib.log.Log
import java.io.File

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
        Constants.init(ApplicationContext!!)
        AppConstants.init()
        initLog()
        Log.i(TAG, "attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()
    }

    fun initLog() {
        var logFile = File(AppConstants.AppDir.getLogFileDir(), "log.txt")
        Log.init(logFile.absolutePath)
        Log.setDebugModel(BuildConfig.DEBUG)
    }
}