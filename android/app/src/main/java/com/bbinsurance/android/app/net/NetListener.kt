package com.bbinsurance.android.app.net

/**
 * Created by jiaminchen on 2017/10/25.
 */
interface NetListener {
    fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse)
    fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse)
}