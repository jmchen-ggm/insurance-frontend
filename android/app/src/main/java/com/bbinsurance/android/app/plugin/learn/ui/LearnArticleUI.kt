package com.bbinsurance.android.app.plugin.learn.ui

import android.view.View
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.BaseListActivity
import com.bbinsurance.android.app.ui.adapter.ArticleAdapter
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter

/**
 * Created by jiaminchen on 17/12/20.
 */
class LearnArticleUI : BaseListActivity() {

    private var articleAdapter: ArticleAdapter? = null
    override fun getAdapter(): BBBaseAdapter {
        if (articleAdapter == null) {
            articleAdapter = ArticleAdapter(this)
        }
        return articleAdapter!!
    }

    override fun initView() {
        super.initView()
        setBBTitle(R.string.menu_learn_text)
        setBackBtn(true, View.OnClickListener { finish() })
    }

    override fun getLayoutId(): Int {
        return R.layout.learn_article_ui
    }
}