package com.bbinsurance.android.app

import com.bbinsurance.android.lib.Constants
import java.io.File

/**
 * Created by jiaminchen on 2017/10/24.
 */
class AppConstants {
    companion object {
        val LOG_PATH = "log";
        val REQUST_PERMISSION = arrayOf(
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE"
        )

        fun init () {
            AppDir.initAppSDCardDir()
        }
    }

    class AppDir {
        companion object {
            val APP_ROOT_DIR = "bb-insurance";
            val LOG_DIR = "log"
            fun initAppSDCardDir() {
                var appSdcardRootDir = File(Constants.SDCARD_ROOT, APP_ROOT_DIR)
                if (!appSdcardRootDir.exists()) {
                    appSdcardRootDir.mkdirs()
                }
                var logDir = File(appSdcardRootDir, LOG_DIR)
                if (!logDir.exists()) {
                    logDir.mkdirs()
                }
            }

            fun getLogFileDir() : File {
                return File(Constants.SDCARD_ROOT, APP_ROOT_DIR + "/" + LOG_DIR)
            }
        }
    }
}