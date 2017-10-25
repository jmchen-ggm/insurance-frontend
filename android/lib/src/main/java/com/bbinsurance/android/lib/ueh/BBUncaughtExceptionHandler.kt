package com.bbinsurance.android.lib.ueh

import com.bbinsurance.android.lib.log.BBLog
import java.io.ByteArrayOutputStream
import java.io.PrintWriter

/**
 * Created by jiaminchen on 2017/10/25.
 */
class BBUncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
    private val TAG = "BB.BBUncaughtExceptionHandler"
    override fun uncaughtException(thread: Thread?, throwable: Throwable?) {
        var bos = ByteArrayOutputStream()
        var printWriter = PrintWriter(bos)
        var t = throwable
        while (t!!.cause != null) {
            t = t.cause
        }
        t.printStackTrace(printWriter)
        var exString = toVisualString(bos.toString())
        bos.close()
        BBLog.e(TAG, t, "uncaughtException\n")
    }

    private fun toVisualString(src: String?): String? {
        var cutFlg = false
        if (null == src) {
            return null
        }
        val chr = src.toCharArray() ?: return null
        var i = 0
        while (i < chr.size) {
            if (chr[i].toInt() > 127) {
                chr[i] = 0.toChar()
                cutFlg = true
                break
            }
            i++
        }
        return if (cutFlg) {
            String(chr, 0, i)
        } else {
            src
        }
    }
}