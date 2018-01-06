package com.bbinsurance.android.lib.log

import java.io.*
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

/**
 * Created by jiaminchen on 2017/10/23.
 */
class MMapFileStorage {
    private val MAX_BUFFER_SIZE = 150 * 1024L;
    private val HEADER_OFFSET = 4;

    private var maxTempFileLength = MAX_BUFFER_SIZE
    private var targetFile: File
    private var tempFile: File

    constructor(targetFilePath : String) {
        targetFile = File(targetFilePath);
        tempFile = File(targetFile.parentFile, "temp-log.txt");
        init();
    }

    private var tempBuffer : MappedByteBuffer ? = null
    private var tempRandomAccessFile : RandomAccessFile? = null

    fun init() {
        if (!targetFile.parentFile.exists()) {
            targetFile.parentFile.mkdirs();
        }
        if (tempFile.exists()) {
            copyAppendTargetFile(tempFile, targetFile)
            tempFile.delete()
        }
    }

    private var currentIndex = HEADER_OFFSET;

    fun appendToBuffer(data : ByteArray, forceAppendToTargetFile : Boolean) {
        try {

            if (tempRandomAccessFile == null) {
                if (tempFile.exists()) {
                    tempFile.createNewFile()
                }
                tempRandomAccessFile = RandomAccessFile(tempFile, "rw")
            }
            if (tempBuffer == null) {
                tempBuffer = tempRandomAccessFile!!.channel.map(FileChannel.MapMode.READ_WRITE, 0, maxTempFileLength);
            }
            if (tempBuffer == null) {
                return;
            }
            if (currentIndex + data.size > maxTempFileLength || forceAppendToTargetFile) {
                tempBuffer!!.force();
                try {
                    tempRandomAccessFile!!.close();
                } catch (e : Exception) {
                }
                copyAppendTargetFile(tempFile, targetFile)
                currentIndex = HEADER_OFFSET
                tempFile.delete()
                tempFile.createNewFile()
                tempRandomAccessFile = RandomAccessFile(tempFile, "rw")
                tempBuffer = tempRandomAccessFile!!.channel.map(FileChannel.MapMode.READ_WRITE, 0, maxTempFileLength);
                tempBuffer!!.putInt(currentIndex - HEADER_OFFSET);
            }
            if (data.size >= 0) {
                tempBuffer!!.position(currentIndex)
                tempBuffer!!.put(data)
                tempBuffer!!.position(0)
                currentIndex += data.size
                tempBuffer!!.putInt(currentIndex - HEADER_OFFSET)
            }
        } catch (e : Exception) {

        }
    }

    private fun copyAppendTargetFile (src : File, dest : File) {
        var fos : FileOutputStream ? = null;
        var dis : DataInputStream ? = null;
        try {
            fos = FileOutputStream(dest, true);
            var fis = FileInputStream(src)
            dis = DataInputStream(fis);
            var tempLogLength = dis.readInt()
            var buffer = ByteArray(1024)
            var read : Int
            var readLength = 0
            var canRead : Int
            while (true) {
                if (readLength + buffer.size > tempLogLength) {
                    canRead = tempLogLength - readLength
                } else {
                    canRead = buffer.size
                }
                if (canRead <= 0) {
                    break
                }
                read = dis.read(buffer, 0, canRead)
                if (read > 0) {
                    fos.write(buffer, 0, read)
                    readLength += read
                } else {
                    break
                }
            }
        } catch (e : Exception) {
        } finally {
            if (dis != null) {
                try {
                    dis.close()
                } catch (e: IOException) {
                }

            }
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                }
            }
        }
    }
}