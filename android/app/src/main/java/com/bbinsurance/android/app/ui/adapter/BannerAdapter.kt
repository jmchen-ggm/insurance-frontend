package com.bbinsurance.android.app.ui.adapter

import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.protocol.BBInsurance
import com.bbinsurance.android.app.protocol.BBListInsuranceRequest
import com.bbinsurance.android.app.protocol.BBListInsuranceResponse
import com.bbinsurance.android.app.ui.component.BannerBaseUIComponent
import com.bbinsurance.android.app.ui.item.BannerDataItem
import com.bbinsurance.android.lib.Util
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator

/**
 * Created by jiaminchen on 17/12/24.
 */
class BannerAdapter : CBViewHolderCreator<BannerDataItem> {
    private val BANNER_CONFIG_KEY = "banner_config_key";
    private var uiComponent : BannerBaseUIComponent<BBInsurance>
    constructor(uiComponent: BannerBaseUIComponent<BBInsurance>) {
        this.uiComponent = uiComponent
    }

    override fun createHolder(): BannerDataItem {
        return BannerDataItem()
    }

    var bannerList : List<BBInsurance> = ArrayList<BBInsurance>()

    fun updateBannerList() {
        var bannerStr = BBCore.Instance.configCore.storage.getConfigValue(BANNER_CONFIG_KEY, "")
        if (!Util.isNullOrNil(bannerStr)) {
            bannerList = JSON.parseArray(bannerStr, BBInsurance::class.java)
        }
        refreshRecommendInsuranceList()
    }

    private fun refreshRecommendInsuranceList() {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncListInsurance, ProtocolConstants.URI.DataBin)
        var listInsuranceRequest = BBListInsuranceRequest()
        listInsuranceRequest.StartIndex = 0
        listInsuranceRequest.PageSize = -1
        netRequest.body = JSON.toJSONString(listInsuranceRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetTaskCancel(netRequest: NetRequest) {
            }

            override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var listInsuranceResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                            BBListInsuranceResponse::class.java)
                    bannerList = listInsuranceResponse.InsuranceList
                    uiComponent.getConvenientBanner().notifyDataSetChanged()
                }
            }

            override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var listInsuranceResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                            BBListInsuranceResponse::class.java)
                    var value = JSON.toJSONString(listInsuranceResponse.InsuranceList)
                    BBCore.Instance.configCore.storage.insertConfig(BANNER_CONFIG_KEY, value)
                }
            }
        })
    }
}