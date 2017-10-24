package com.bbinsurance.android.lib

import android.content.Context
import android.os.Environment

/**
 * Created by jiaminchen on 2017/10/24.
 */
open class Constants {

    companion object {
        var SDCARD_ROOT : String = ""
        var DATA_ROOT : String = ""

        fun init(context : Context) {
            SDCARD_ROOT = Environment.getExternalStorageDirectory().absolutePath;
            DATA_ROOT = context.filesDir.absolutePath + "/"
        }
    }

    open class LogLevel {
        companion object {
            val VERBOSE = 0;
            val DEBUG = 1;
            val INFO = 2;
            val WARN = 3;
            val ERROR = 4;
        }
    }

    open class Version {
        var ClientVersion : Int = 0x10000000;
    }
}