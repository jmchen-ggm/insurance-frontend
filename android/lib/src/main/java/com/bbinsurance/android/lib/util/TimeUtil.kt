package com.bbinsurance.android.lib.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jiaminchen on 17/11/17.
 */
class TimeUtil {
    companion object {
        var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        fun formatTime(timestamp : Long) : String {
            return formatter.format(Date(timestamp))
        }
    }
}