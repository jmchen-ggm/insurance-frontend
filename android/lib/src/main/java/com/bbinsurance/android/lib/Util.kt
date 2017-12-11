package com.bbinsurance.android.lib

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by jiaminchen on 2017/10/23.
 */
class Util {
    companion object {
        fun isNullOrNil(str : String ?) : Boolean {
            return str == null || str.isEmpty()
        }

        fun nullAs(str : String, defaultStr: String) : String {
            if (isNullOrNil(str)) {
                return defaultStr
            } else {
                return str
            }
        }

        fun checkBit(value : Long, bit : Long) : Boolean {
            return (value and bit) > 0
        }

        fun MD5(str : String) : String {
            try {
                val instance: MessageDigest = MessageDigest.getInstance("MD5")//获取md5加密对象
                val digest:ByteArray = instance.digest(str.toByteArray())//对字符串加密，返回字节数组
                var sb = StringBuffer()
                for (b in digest) {
                    var i :Int = b.toInt() and 0xff//获取低八位有效值
                    var hexString = Integer.toHexString(i)//将整数转化为16进制
                    if (hexString.length < 2) {
                        hexString = "0" + hexString//如果是一位的话，补0
                    }
                    sb.append(hexString)
                }
                return sb.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}