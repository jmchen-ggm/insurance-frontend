package com.bbinsurance.android.app.core

import android.os.Looper
import com.bbinsurance.android.app.AppConstants
import com.bbinsurance.android.app.Application
import com.bbinsurance.android.app.BuildConfig
import com.bbinsurance.android.app.plugin.account.AccountCore
import com.bbinsurance.android.app.net.NetCore
import com.bbinsurance.android.app.plugin.comment.CommentCore
import com.bbinsurance.android.app.plugin.config.ConfigCore
import com.bbinsurance.android.lib.BBHandler
import com.bbinsurance.android.lib.Constants
import com.bbinsurance.android.lib.log.BBLog
import com.bbinsurance.android.lib.ueh.BBUncaughtExceptionHandler
import java.io.File

/**
 * Created by jiaminchen on 2017/10/25.
 */
class BBCore {

    companion object {

        lateinit var Instance: BBCore

        fun initCore() {
            Constants.init(Application.ApplicationContext)
            AppConstants.init()
            initLog()
            Thread.setDefaultUncaughtExceptionHandler(BBUncaughtExceptionHandler())
            Instance = BBCore()
        }

        private fun initLog() {
            var logFile = File(AppConstants.AppDir.getLogFileDir(), "log.txt")
            BBLog.init(logFile.absolutePath)
            BBLog.setDebugModel(BuildConfig.DEBUG)
        }
    }

    var netCore : NetCore
    var threadCore : ThreadCore
    var dbCore : DBCore
    var uiHandler : BBHandler

    // subLogicCore
    var accountCore : AccountCore
    var configCore : ConfigCore
    var commentCore : CommentCore

    constructor() {
        netCore = NetCore()
        threadCore = ThreadCore()
        configCore = ConfigCore()
        dbCore = DBCore()
        uiHandler = BBHandler(Looper.getMainLooper(), "Main")

        accountCore = AccountCore()
        commentCore = CommentCore()
    }
}