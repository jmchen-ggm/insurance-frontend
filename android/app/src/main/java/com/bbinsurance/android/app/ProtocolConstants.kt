package com.bbinsurance.android.app

/**
 * Created by jiaminchen on 2017/10/25.
 */
open class ProtocolConstants {
    companion object {

    }

    open class FunId {
        companion object {
            val RecommendationInsurance = 1;
            val InsuranceList = 2;
            val CompareInsurance = 3;
        }
    }

    open class URI {
        companion object {
            val DataBin = "/data-bin/"
            val ConfigBin = "/config-bin/"
        }
    }

    open class URL {
        companion object {
            val FileServer = "http://120.78.175.235:8081/"
            val Data = "http://bb-insurance.cn/insuranceS/data/information"
        }
    }

    open class RequestType {
        companion object {
            val GET = "GET"
            val POST = "POST"
        }
    }

    open class HttpContentType {
        companion object {
            val JSON = ""
        }
    }
}