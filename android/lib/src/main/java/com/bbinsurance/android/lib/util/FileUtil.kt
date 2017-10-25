package com.bbinsurance.android.lib.util

import com.bbinsurance.android.lib.log.BBLog
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * Created by jiaminchen on 2017/10/25.
 */
class FileUtil {
    companion object {
        private val TAG = "BB.FileUtil"

        fun readStream(inputStream : InputStream) : ByteArray {
            var outputBuffer = ByteArrayOutputStream()
            var buffer = ByteArray(1024)
            var read = 0
            while (true) {
                try {
                    read = inputStream.read(buffer, 0, buffer.size)
                } catch (e : Exception) {
                    BBLog.e(TAG, e, "readStream")
                }
                if (read > 0) {
                    outputBuffer.write(buffer, 0, read)
                } else {
                    break;
                }
            }
            return outputBuffer.toByteArray()
        }

        fun closeInputStream(inputStream: InputStream) {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e : Exception){
                }
            }
        }

        fun closeOutputStream(outputStream: OutputStream) {
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e : Exception){
                }
            }
        }
    }
}