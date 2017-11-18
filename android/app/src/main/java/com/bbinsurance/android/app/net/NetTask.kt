package com.bbinsurance.android.app.net

import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.protocol.BBReq
import com.bbinsurance.android.app.protocol.BBResp
import com.bbinsurance.android.lib.BBHandler
import com.bbinsurance.android.lib.Util
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
        BBLog.i(TAG, "doRequest %s", netRequest.info())
        var bbReq = BBReq()
        bbReq.Bin.FunId = netRequest.funId
        bbReq.Bin.URI = netRequest.uri
        bbReq.Bin.SessionId = netRequest.sessionId
        bbReq.Bin.Timestamp = netRequest.timestamp

        bbReq.Header.Uin = BBCore.Instance.accountCore.getUIN()

        bbReq.Body = JSON.parse(netRequest.body) as JSON
        doRequest(bbReq)
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

    private fun doRequest(bbReq: BBReq) {
        var requestUrl = ""
        var requestMethod = ""
        if (bbReq.Bin.URI.equals(ProtocolConstants.URI.DataBin)) {
            requestUrl = ProtocolConstants.URL.Data
            requestMethod = ProtocolConstants.RequestType.POST
        }
        if (Util.isNullOrNil(requestUrl)) {
            BBLog.e(TAG, "can not get request url")
            return;
        }
        var requestBodyBytes = JSON.toJSONString(bbReq).toByteArray()
        var url = URL(requestUrl)
        var urlConnection  = url.openConnection() as HttpURLConnection
        urlConnection.connectTimeout = 60 * 1000
        urlConnection.readTimeout = 120 * 1000
        urlConnection.instanceFollowRedirects = true
        urlConnection.requestMethod = requestMethod
        if (requestMethod == ProtocolConstants.RequestType.POST) {
            urlConnection.doOutput = true
            urlConnection.connect()
            urlConnection.outputStream.write(requestBodyBytes)
            FileUtil.closeOutputStream(urlConnection.outputStream)
        } else if (requestMethod == ProtocolConstants.RequestType.GET) {
            urlConnection.connect()
        }
        netResponse.respCode = urlConnection.responseCode
        netResponse.responseBody = FileUtil.readStream(urlConnection.inputStream)
        if (bbReq.Bin.URI.equals(ProtocolConstants.URI.DataBin)) {
            netResponse.bbResp = JSON.parseObject(String(netResponse.responseBody), BBResp::class.java)
        }
    }
}