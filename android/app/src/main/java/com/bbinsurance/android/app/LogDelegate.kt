package com.bbinsurance.android.app

import com.bbinsurance.android.lib.log.BBLog
import com.facebook.common.logging.FLog
import com.facebook.common.logging.LoggingDelegate

/**
 * Created by jiaminchen on 17/12/25.
 */
class LogDelegate : LoggingDelegate {
    override fun wtf(tag: String?, msg: String?) {

    }

    override fun wtf(tag: String?, msg: String?, tr: Throwable?) {
    }

    override fun getMinimumLoggingLevel(): Int {
        return FLog.DEBUG
    }

    override fun w(tag: String?, msg: String?) {
        BBLog.w(tag!!, msg!!);
    }

    override fun w(tag: String?, msg: String?, tr: Throwable?) {
        BBLog.w(tag!!, msg!!)
    }

    override fun v(tag: String?, msg: String?) {
        BBLog.v(tag!!, msg!!)
    }

    override fun v(tag: String?, msg: String?, tr: Throwable?) {
        BBLog.v(tag!!, msg!!)
    }

    override fun log(priority: Int, tag: String?, msg: String?) {

    }

    override fun setMinimumLoggingLevel(level: Int) {

    }

    override fun isLoggable(level: Int): Boolean {
        return true
    }

    override fun i(tag: String?, msg: String?) {
        BBLog.i(tag!!, msg!!)
    }

    override fun i(tag: String?, msg: String?, tr: Throwable?) {
        BBLog.i(tag!!, msg!!)
    }

    override fun e(tag: String?, msg: String?) {
        BBLog.e(tag!!, msg!!)
    }

    override fun e(tag: String?, msg: String?, tr: Throwable?) {
        BBLog.e(tag!!, tr!!, msg!!)
    }

    override fun d(tag: String?, msg: String?) {
        BBLog.d(tag!!, msg!!)
    }

    override fun d(tag: String?, msg: String?, tr: Throwable?) {
        BBLog.d(tag!!, msg!!)
    }
}