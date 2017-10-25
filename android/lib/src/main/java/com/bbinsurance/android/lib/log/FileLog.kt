package com.bbinsurance.android.lib.log

import android.os.Process
import com.bbinsurance.android.lib.Constants
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jiaminchen on 2017/10/24.
 */
class FileLog : IBBLog {

    private val MAX_LOG_LENGTH = 5 * 1024 * 1024L

    private var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
    private var mmappedFileStorage : MMapFileStorage

    constructor(logPath : String) {
        var logFile = File(logPath);
        if (logFile.length() > MAX_LOG_LENGTH) {
            logFile.delete();
        }
        mmappedFileStorage = MMapFileStorage(logFile.absolutePath);
    }

    private var logLevel : Int = Constants.LogLevel.DEBUG

    @Synchronized fun appendToBuffer(log : String, appendToRealLog : Boolean) {
        mmappedFileStorage.appendToBuffer(log.toByteArray(), appendToRealLog)
    }

    override fun d(tag: String, log: String) {
        if (logLevel <= Constants.LogLevel.DEBUG) {
            var totalLog = generateTotalLog("D", tag, log)
            appendToBuffer(totalLog, false)
        }
    }

    override fun i(tag: String, log: String) {
        if (logLevel <= Constants.LogLevel.INFO) {
            var totalLog = generateTotalLog("I", tag, log)
            appendToBuffer(totalLog, false)
        }
    }

    override fun w(tag: String, log: String) {
        if (logLevel <= Constants.LogLevel.WARN) {
            var totalLog = generateTotalLog("W", tag, log)
            appendToBuffer(totalLog, false)
        }
    }

    override fun e(tag: String, log: String) {
        if (logLevel <= Constants.LogLevel.ERROR) {
            var totalLog = generateTotalLog("E", tag, log)
            appendToBuffer(totalLog, false)
        }
    }

    override fun v(tag: String, log: String) {
        if (logLevel <= Constants.LogLevel.VERBOSE) {
            var totalLog = generateTotalLog("V", tag, log)
            appendToBuffer(totalLog, false)
        }
    }

    fun generateTotalLog(level : String, tag : String, log : String) : String {
        val timestamp = formatter.format(Date())
        val totalLog = String.format("[%s][%s][%d][%d][%s]: %s\n", level, tag, Process.myPid(),
                Thread.currentThread().id, timestamp, log)
        return totalLog
    }

    override fun getLogLevel(): Int {
        return logLevel
    }

    override fun setLogLevel(logLevel: Int) {
        this.logLevel = logLevel;
    }
}