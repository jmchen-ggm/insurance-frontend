package com.bbinsurance.android.app.net

/**
 * Created by jiaminchen on 2017/10/25.
 */
interface NetListener {
    fun onNetDone(netRequest: NetRequest, netResponse: NetResponse)
}