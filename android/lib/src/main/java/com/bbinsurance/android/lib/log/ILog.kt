package com.bbinsurance.android.lib.log

/**
 * Created by jiaminchen on 2017/10/24.
 */
interface ILog {
    fun v(tag : String, log : String)
    fun d(tag : String, log : String)
    fun i(tag : String, log : String)
    fun w(tag : String, log : String)
    fun e(tag : String, log : String)
    fun getLogLevel () : Int
    fun setLogLevel (logLevel : Int)
}