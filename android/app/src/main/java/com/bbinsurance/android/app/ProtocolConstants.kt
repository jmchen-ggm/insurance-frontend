package com.bbinsurance.android.app

/**
 * Created by jiaminchen on 2017/10/25.
 */
open class ProtocolConstants {
    companion object {

    }

    open class FunId {
        companion object {
            val FuncListArticle = 1
            val FuncListCompany = 2
            val FuncListInsurance = 3
            val FuncListComment = 4
            val FuncCreateComment = 5
            val FuncViewComment = 6
        }
    }

    open class URI {
        companion object {
            val DataBin = "data-bin"
            val ConfigBin = "config-bin"
        }
    }

    open class URL {
        companion object {
            val FileServer = "http://120.78.175.235:8081/"
            val Data = "http://120.78.175.235:8081/data-bin"
        }
    }

    open class RequestType {
        companion object {
            val GET = "GET"
            val POST = "POST"
        }
    }

    open class CommentFlag {
        companion object {
            val CREATED : Long = 0x1
            val IMPORTANCE : Long = 0x2
        }
    }

    open class HttpContentType {
        companion object {
            val JSON = "application/json"
        }
    }
}