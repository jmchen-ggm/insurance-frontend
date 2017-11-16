package com.bbinsurance.android.app.protocol

import com.alibaba.fastjson.JSON

/**
 * Created by jiaminchen on 17/11/17.
 */
class BBReq {
    var Bin = BBBin()
    var Header = BBReqHeader()
    var Body: JSON? = null
}