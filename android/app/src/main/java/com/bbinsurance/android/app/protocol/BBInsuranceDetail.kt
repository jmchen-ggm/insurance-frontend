package com.bbinsurance.android.app.protocol

/**
 * Created by jiaminchen on 2017/12/20.
 */
class BBInsuranceDetail {
    var Id = 0L
    var Name= ""
    var Desc = ""
    var InsuranceType = BBInsuranceType()
    var Company = BBCompany()
    var AgeFrom = 0
    var AgeTo = 0
    var AnnualCompensation = 0
    var AnnualPremium = 0
    var Timestamp = 0L
    var ThumbUrl = ""
    var DetailData = ""
}