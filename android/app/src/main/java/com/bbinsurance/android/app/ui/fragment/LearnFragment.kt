package com.bbinsurance.android.app.ui.fragment

import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.ArticleAdapter

/**
 * Created by jiaminchen on 17/11/12.
 */
class LearnFragment : BaseListFragment() {

    private var articleAdapter: ArticleAdapter? = null

    override fun getAdapter(): BBBaseAdapter {
        if (articleAdapter == null) {
            articleAdapter = ArticleAdapter(this)
        }
        return articleAdapter!!
    }

    override fun getLayoutId(): Int {
        return R.layout.learn_fragment_ui
    }
}