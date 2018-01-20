package com.bbinsurance.android.app.plugin.learn.ui

import android.content.Intent
import android.view.View
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.ui.BaseListActivity
import com.bbinsurance.android.app.plugin.learn.ui.adapter.ArticleAdapter
import com.bbinsurance.android.app.plugin.learn.ui.item.ArticleDataItem
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.webview.BBWebViewUI

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

    override fun handleItemClick(view: View, dataItem: BaseDataItem, isHandle: Boolean): Boolean {
        var learnArticleDataItem = dataItem as ArticleDataItem
        var intent = Intent(this, BBWebViewUI::class.java)
        intent.putExtra(UIConstants.IntentKey.KeyTitle, learnArticleDataItem.article.Title)
        intent.putExtra(UIConstants.IntentKey.KeyUrl, learnArticleDataItem.article.Url)
        startActivity(intent)
        return true
    }
}