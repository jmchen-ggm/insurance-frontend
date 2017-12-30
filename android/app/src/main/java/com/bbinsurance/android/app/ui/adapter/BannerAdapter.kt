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
    private var uiComponent : BannerBaseUIComponent<BBInsurance>
    constructor(uiComponent: BannerBaseUIComponent<BBInsurance>) {
        this.uiComponent = uiComponent
    }

    override fun createHolder(): BannerDataItem {
        return BannerDataItem()
    }
}