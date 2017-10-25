package com.bbinsurance.android.app.net

import android.util.Log
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.lib.BBHandler
import com.bbinsurance.android.lib.log.BBLog
import com.bbinsurance.android.lib.util.FileUtil
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by jiaminchen on 2017/10/25.
 */
open class NetTask : Runnable {

    private val TAG = "BB.NetworkTask"

    var netRequest: NetRequest
    var netResponse : NetResponse
    var handler : BBHandler

    constructor(request: NetRequest) {
        this.netRequest = request;
        this.netResponse = NetResponse()
        this.handler = BBCore.Instance.uiHandler
    }

    var netListener : NetListener ? = null

    override fun run() {
        var before = System.currentTimeMillis()
        if (netRequest.uri.startsWith(ProtocolConstants.URI.DataBin)) {
            netRequest.url = ProtocolConstants.URL.Data
            netRequest.requestMethod = ProtocolConstants.RequestType.POST
        }
        doRequest()
        if (netListener != null) {
            handler.post({
                run() {
                    netListener!!.onNetDone(netRequest, netResponse)
                }
            })
        }
        var after = System.currentTimeMillis()
        BBLog.i(TAG, "execute net task use %d respCode %d", after - before, netResponse.respCode)
    }

    private fun doRequest() {
        BBLog.i(TAG, "doRequest %s", netRequest.info())
        var url = URL(netRequest.url)
        var urlConnection  = url.openConnection() as HttpURLConnection
        urlConnection.requestMethod = netRequest.requestMethod
        urlConnection.connectTimeout = 60 * 1000
        urlConnection.readTimeout = 120 * 1000
        urlConnection.doOutput = true
        urlConnection.instanceFollowRedirects = true
        urlConnection.connect()
        urlConnection.outputStream.write(netRequest.requestBody)
        FileUtil.closeOutputStream(urlConnection.outputStream)
        netResponse.respCode = urlConnection.responseCode
        netResponse.responseBody = FileUtil.readStream(urlConnection.inputStream)
    }
}