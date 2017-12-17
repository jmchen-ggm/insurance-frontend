package com.bbinsurance.android.lib.util

import android.content.Context
import android.text.format.DateFormat
import com.bbinsurance.android.lib.R
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

        // format in sns gallery
        fun formatTimeInList(context: Context, time: Long): String {
            val now = Calendar.getInstance()
            val timeCal = Calendar.getInstance()
            timeCal.timeInMillis = time
            val isSameY = now.get(Calendar.YEAR) == timeCal.get(Calendar.YEAR)
            val isSameMonth = isSameY && now.get(Calendar.MONTH) == timeCal.get(Calendar.MONTH)
            val isSameD = isSameY && isSameMonth && now.get(Calendar.DAY_OF_MONTH) == timeCal.get(Calendar.DAY_OF_MONTH)
            var isYesterday = isSameY && isSameMonth && now.get(Calendar.DAY_OF_MONTH) - 1 == timeCal.get(Calendar.DAY_OF_MONTH)
            if (!isYesterday) {
                //是否跨年月了？
                var cal = false
                if (isSameY && now.get(Calendar.MONTH) - 1 == timeCal.get(Calendar.MONTH)) {
                    //同年次月
                    cal = true
                } else if (now.get(Calendar.YEAR) - timeCal.get(Calendar.YEAR) == 1) {
                    //次年
                    cal = true
                }
                if (cal) {
                    now.add(Calendar.DAY_OF_MONTH, -1)
                    isYesterday = now.get(Calendar.YEAR) == timeCal.get(Calendar.YEAR) && now.get(Calendar.MONTH) == timeCal.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) == timeCal.get(Calendar.DAY_OF_MONTH)
                }
            }

            if (isSameD) {
                return formatSnsTime24(context, time) as String
            } else if (isYesterday) {
                return context.getString(R.string.fmt_pre_yesterday) + " " + formatSnsTime24(context, time) as String
            } else {
                var dateStr = ""
                if (isSameY) {
                    dateStr = DateFormat.format(context.getString(R.string.fmt_date), time) as String
                } else {
                    dateStr = DateFormat.format(context.getString(R.string.fmt_longdate), time) as String
                }

                if (dateStr.indexOf("-") > 0) {
                    val mon = timeCal.get(Calendar.MONTH) + 1//dateStr.split("-")[0];
                    val day = timeCal.get(Calendar.DAY_OF_MONTH)//dateStr.split("-")[1];
                    val realMon = getMonth(context, mon)
                    dateStr = day.toString() + " " + realMon
                    if (!isSameY) {
                        dateStr += " " + timeCal.get(Calendar.YEAR)
                    }
                }

                return dateStr + " " + formatSnsTime24(context, time) as String
            }
        }

        private fun formatSnsTime24(context: Context, time: Long): CharSequence {
            return DateFormat.format(context.getString(R.string.fmt_normal_time_24), time)
        }

        private fun getMonth(context: Context, index: Int): String {
            val months = context.resources.getStringArray(R.array.time_month)
            val list = ArrayList<String>()
            list.add("")
            for (i in months.indices) {
                list.add(months[i])
            }
            return if (index >= list.size) {
                ""
            } else list[index]
        }
    }
}