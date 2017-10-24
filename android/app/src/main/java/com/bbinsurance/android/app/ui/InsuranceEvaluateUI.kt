package com.bbinsurance.android.app.ui

import android.os.Bundle
import com.bbinsurance.android.app.R

/**
 * Created by jiaminchen on 2017/10/24.
 */
class InsuranceEvaluateUI : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBBTitle(R.string.menu_evaluate_text)
    }

    override fun getLayoutId(): Int {
        return R.layout.insurance_evaluate_ui
    }
}