package com.bbinsurance.android.app.ui.component

import com.bigkoo.convenientbanner.ConvenientBanner

/**
 * Created by jiaminchen on 17/12/24.
 */
interface BannerBaseUIComponent<T> : BaseUIComponent {
    fun getConvenientBanner() : ConvenientBanner<T>
}