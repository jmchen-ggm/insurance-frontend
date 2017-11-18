package com.bbinsurance.android.app.core

import android.os.Looper
import com.bbinsurance.android.app.AppConstants
import com.bbinsurance.android.app.Application
import com.bbinsurance.android.app.BuildConfig
import com.bbinsurance.android.app.account.AccountCore
import com.bbinsurance.android.app.net.NetCore
import com.bbinsurance.android.lib.BBHandler
import com.bbinsurance.android.lib.Constants
import com.bbinsurance.android.lib.log.BBLog
import com.bbinsurance.android.lib.ueh.BBUncaughtExceptionHandler
import com.facebook.drawee.backends.pipeline.Fresco
import java.io.File

/**
 * Created by jiaminchen on 2017/10/25.
 */
class BBCore {

    companion object {

        var Instance: BBCore = BBCore()

        fun initCore() {
            Constants.init(Application.ApplicationContext!!)
            AppConstants.init()
            initLog()
            Thread.setDefaultUncaughtExceptionHandler(BBUncaughtExceptionHandler())
        }

        private fun initLog() {
            var logFile = File(AppConstants.AppDir.getLogFileDir(), "log.txt")
            BBLog.init(logFile.absolutePath)
            BBLog.setDebugModel(BuildConfig.DEBUG)
        }
    }

    var netCore: NetCore
    var accountCore: AccountCore
    var threadCore : ThreadCore
    var uiHandler : BBHandler

    constructor() {
        netCore = NetCore()
        accountCore = AccountCore()
        threadCore = ThreadCore()
        uiHandler = BBHandler(Looper.getMainLooper())
    }
}