package com.bbinsurance.android.app.protocol

import com.alibaba.fastjson.JSON

/**
 * Created by jiaminchen on 17/11/17.
 */
class BBResp {
    var Bin = BBBin()
    var Header = BBRespHeader()
    var Body: JSON? = null
}