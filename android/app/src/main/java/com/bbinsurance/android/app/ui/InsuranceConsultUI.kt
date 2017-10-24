package com.bbinsurance.android.app.ui

import android.os.Bundle
import com.bbinsurance.android.app.R

/**
 * Created by jiaminchen on 2017/10/24.
 */
class InsuranceConsultUI : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBBTitle(R.string.menu_consult_text)
    }

    override fun getLayoutId(): Int {
        return R.layout.insurance_consult_ui
    }
}