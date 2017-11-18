package com.bbinsurance.android.app.net

import com.bbinsurance.android.app.protocol.BBResp

/**
 * Created by jiaminchen on 2017/10/25.
 */
class NetResponse {
    var respCode = 200
    var responseBody: ByteArray = ByteArray(0)
    var bbResp = BBResp()
}