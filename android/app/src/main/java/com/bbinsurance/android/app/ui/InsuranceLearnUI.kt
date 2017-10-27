package com.bbinsurance.android.app.ui

import android.os.Bundle
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.LearnArticleAdapter

/**
 * Created by jiaminchen on 2017/10/24.
 */
class InsuranceLearnUI : BaseListViewUI() {

    private var learnArticleAdapter : LearnArticleAdapter ? = null

    override fun getAdapter(): BBBaseAdapter {
        if (learnArticleAdapter == null) {
            learnArticleAdapter = LearnArticleAdapter(this)
        }
        return learnArticleAdapter!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBBTitle(R.string.menu_learn_text)
    }

    override fun getLayoutId(): Int {
        return R.layout.insurance_learn_ui
    }


}