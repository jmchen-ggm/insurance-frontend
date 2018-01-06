package com.bbinsurance.android.app.core

import android.os.Looper
import com.bbinsurance.android.app.AppConstants
import com.bbinsurance.android.app.BBApplication
import com.bbinsurance.android.app.BuildConfig
import com.bbinsurance.android.app.UpdateInfo
import com.bbinsurance.android.app.net.NetCore
import com.bbinsurance.android.app.plugin.account.AccountCore
import com.bbinsurance.android.app.plugin.comment.CommentCore
import com.bbinsurance.android.app.plugin.config.ConfigCore
import com.bbinsurance.android.lib.BBHandler
import com.bbinsurance.android.lib.Constants
import com.bbinsurance.android.lib.log.BBLog
import com.bbinsurance.android.lib.ueh.BBUncaughtExceptionHandler
import com.bbinsurance.android.lib.util.TimeUtil
import java.io.File

/**
 * Created by jiaminchen on 2017/10/25.
 */
class BBCore {

    companion object {
        private val TAG = "BB.BBCore"
        lateinit var Instance: BBCore

        fun initCore() {
            Constants.init(BBApplication.ApplicationContext)
            AppConstants.init()
            initLog()
            var updateInfo = initVersion()
            Thread.setDefaultUncaughtExceptionHandler(BBUncaughtExceptionHandler())
            Instance = BBCore()
            Instance.initCore(updateInfo)
        }

        private fun initLog() {
            var logFile = File(AppConstants.AppDir.getLogFileDir(), TimeUtil.getDateString(System.currentTimeMillis()) + "-log.txt")
            BBLog.init(logFile.absolutePath)
            BBLog.setDebugModel(BuildConfig.DEBUG)
        }

        private fun initVersion() : UpdateInfo {
            var sp = BBApplication.ApplicationContext.getSharedPreferences("Version", 0)
            var updateInfo = UpdateInfo()
            updateInfo.lastVersion = sp.getLong("ClientVersion", 0)
            updateInfo.updated = updateInfo.lastVersion != AppConstants.AppVersion.ClientVersion
            sp.edit().putLong("ClientVersion", AppConstants.AppVersion.ClientVersion).commit()
            BBLog.i(TAG, "initVersion lastVersion %x currentVersion %x versionName %s", updateInfo.lastVersion, AppConstants.AppVersion.ClientVersion, AppConstants.AppVersion.getVersionName())
            return updateInfo
        }
    }

    lateinit var netCore: NetCore
    lateinit var threadCore: ThreadCore
    lateinit var dbCore: DBCore
    lateinit var uiHandler: BBHandler

    // subLogicCore
    lateinit var accountCore: AccountCore
    lateinit var configCore: ConfigCore
    lateinit var commentCore: CommentCore

    constructor() {

    }

    fun initCore(updateInfo : UpdateInfo) {
        // config core一定要早一点启动
        configCore = ConfigCore()
        netCore = NetCore()
        threadCore = ThreadCore()
        dbCore = DBCore()
        uiHandler = BBHandler(Looper.getMainLooper(), "Main")

        accountCore = AccountCore()
        commentCore = CommentCore()
    }
}