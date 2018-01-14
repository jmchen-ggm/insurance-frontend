package com.bbinsurance.android.app.plugin.init

import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.CompanyEntity
import com.bbinsurance.android.app.db.entity.InsuranceTypeEntity
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.protocol.*
import com.bbinsurance.android.lib.util.TimeUtil

/**
 * Created by jiaminchen on 18/1/13.
 */
class GetInsuranceBaseDataTask : Runnable {
    companion object {
        val ConfigGetInsuranceBaseDataTimestampKey = "ConfigGetInsuranceBaseDataTimestampKey"
    }
    override fun run() {
        var lastUpdateTime = BBCore.Instance.configCore.storage.getConfigValue(ConfigGetInsuranceBaseDataTimestampKey, "0").toLong()
        if (TimeUtil.compareDate(lastUpdateTime, System.currentTimeMillis()) == 0) {
            return
        } else {
            BBCore.Instance.configCore.storage.insertConfig(ConfigGetInsuranceBaseDataTimestampKey, System.currentTimeMillis().toString())
        }

        var netRequest = NetRequest(ProtocolConstants.FunId.FuncListInsuranceType, ProtocolConstants.URI.DataBin)
        var listInsuranceTypeRequest = BBListInsuranceTypeRequest()
        listInsuranceTypeRequest.StartIndex = 0
        listInsuranceTypeRequest.PageSize = -1
        netRequest.body = JSON.toJSONString(listInsuranceTypeRequest)
        var netResponse = BBCore.Instance.netCore.startRequestSync(netRequest)
        if (netResponse.respCode == 200) {
            var listInsuranceTypeResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                    BBListInsuranceTypeResponse::class.java)
            for (insuranceType : BBInsuranceType in listInsuranceTypeResponse.InsuranceTypeList) {
                var entity = InsuranceTypeEntity()
                entity.Id = insuranceType.Id
                entity.Name = insuranceType.Name
                entity.Desc = insuranceType.Desc
                entity.ThumbUrl = insuranceType.ThumbUrl
                BBCore.Instance.dbCore.db.insuranceTypeDao().insertInsuranceType(entity)
            }
        }
        netRequest = NetRequest(ProtocolConstants.FunId.FuncListCompany, ProtocolConstants.URI.DataBin)
        var listCompanyRequest = BBListCompanyRequest()
        listCompanyRequest.StartIndex = 0
        listCompanyRequest.PageSize = -1
        netRequest.body = JSON.toJSONString(listCompanyRequest)
        netResponse = BBCore.Instance.netCore.startRequestSync(netRequest)
        var listCompanyResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                BBListCompanyResponse::class.java)
        for (company : BBCompany in listCompanyResponse.CompanyList) {
            var entity = CompanyEntity()
            entity.Id = company.Id
            entity.Name = company.Name
            entity.Desc = company.Desc
            entity.ThumbUrl = company.ThumbUrl
            BBCore.Instance.dbCore.db.companyDao().insertCompany(entity)
        }
    }
}