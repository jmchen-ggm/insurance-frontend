package com.bbinsurance.android.app.ui.fragment

import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.LearnArticleAdapter

/**
 * Created by jiaminchen on 17/11/12.
 */
class LearnFragment : BaseListFragment() {

    private var learnArticleAdapter : LearnArticleAdapter? = null

    override fun getAdapter(): BBBaseAdapter {
        if (learnArticleAdapter == null) {
            learnArticleAdapter = LearnArticleAdapter(this)
        }
        return learnArticleAdapter!!
    }

    override fun getLayoutId(): Int {
        return R.layout.learn_fragment_ui
    }
}