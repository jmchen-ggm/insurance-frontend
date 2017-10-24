package com.bbinsurance.android.lib.log

import java.io.File

/**
 * Created by jiaminchen on 2017/10/24.
 */
open class Log {
    companion object {
        private val TAG = "APP.Debug"
        private var logImpl : ILog ? = null
        private var debugMode : Boolean = false;

        fun init(logFilePath : String) {
            this.logImpl = FileLog(logFilePath);
        }

        fun v(tag : String, format : String, vararg args : Any) {
            var log = String.format(format, args)
            logImpl!!.v(tag, log)
            if (debugMode) {
                android.util.Log.v(TAG, String.format("[%s] %s", tag, log))
            }
        }

        fun d(tag : String, format : String, vararg args : Any) {
            var log = String.format(format, args)
            logImpl!!.d(tag, log)
            if (debugMode) {
                android.util.Log.d(TAG, String.format("[%s] %s", tag, log))
            }
        }

        fun i(tag : String, format : String, vararg args : Any) {
            var log = String.format(format, args)
            logImpl!!.i(tag, log)
            if (debugMode) {
                android.util.Log.i(TAG, String.format("[%s] %s", tag, log))
            }
        }

        fun w(tag : String, format : String, vararg args : Any) {
            var log = String.format(format, args)
            logImpl!!.w(tag, log)
            if (debugMode) {
                android.util.Log.w(TAG, String.format("[%s] %s", tag, log))
            }
        }

        fun e(tag : String, format : String, vararg args : Any) {
            var log = String.format(format, args)
            logImpl!!.e(tag, log)
            if (debugMode) {
                android.util.Log.e(TAG, String.format("[%s] %s", tag, log))
            }
        }

        fun e(tag : String, throwable: Throwable, format : String, vararg args : Any) {
            var log = String.format(format, args)
            log += " " + android.util.Log.getStackTraceString(throwable);
            logImpl!!.e(tag, log)
            if (debugMode) {
                android.util.Log.e(TAG, String.format("[%s] %s", tag, log))
            }
        }

        fun setLogLevel(logLevel: Int) {
            logImpl!!.setLogLevel(logLevel)
        }

        fun setDebugModel(debugMode : Boolean) {
            this.debugMode = debugMode;
        }
    }
}