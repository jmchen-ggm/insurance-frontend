package com.bbinsurance.android.app

import com.bbinsurance.android.lib.Constants
import java.io.File

/**
 * Created by jiaminchen on 2017/10/24.
 */
class AppConstants {

    class AppVersion {
        companion object {
            val ClientVersion : Long = 0x21010110

            fun getVersionName() : String {
                var version1 = ClientVersion.and (0x0F000000).ushr(4 * 6).toString()
                var version2 = ClientVersion.and (0x00FF0000).ushr(4 * 4).toString()
                var version3 = ClientVersion.and (0x0000FF00).ushr(4 * 2).toString()
                return "$version1.$version2.$version3"
            }
        }
    }

    companion object {
        val LOG_PATH = "log";
        val REQUST_PERMISSION = arrayOf(
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.INTERNET"
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