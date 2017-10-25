package com.bbinsurance.android.lib

/**
 * Created by jiaminchen on 2017/10/23.
 */
class Util {
    companion object {
        fun isNullOrNil(str : String) : Boolean {
            return str == null || str.isEmpty()
        }

        fun nullAs(str : String, defaultStr: String) : String {
            if (isNullOrNil(str)) {
                return defaultStr
            } else {
                return str
            }
        }
    }
}