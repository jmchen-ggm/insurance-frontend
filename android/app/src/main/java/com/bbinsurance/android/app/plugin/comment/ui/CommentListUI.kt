package com.bbinsurance.android.app.plugin.comment.ui

import android.content.Intent
import android.view.View
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.plugin.account.ui.LoginUI
import com.bbinsurance.android.app.ui.BaseListActivity
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentListUI : BaseListActivity() {

    var adapter: CommentAdapter? = null
    override fun getAdapter(): BBBaseAdapter {
        if (adapter == null) {
            adapter = CommentAdapter(this)
        }
        return adapter!!
    }

    override fun initView() {
        super.initView()
        setBBTitle(R.string.comment)
        setBackBtn(true, View.OnClickListener { finish() })
        setOptionBtn(R.drawable.add_icon, View.OnClickListener {
            if (BBCore.Instance.accountCore.loginService.isLogin()) {
                var intent = Intent(this, AddCommentUI::class.java)
                startActivity(intent)
            } else {
                var intent = Intent(this, LoginUI::class.java)
                startActivity(intent)
            }
        })

        adapter?.refreshCommentList()
    }

    override fun supportLoadMore(): Boolean {
        return true
    }

    override fun onLoadMore() {
        adapter?.refreshCommentList()
    }

    override fun getLayoutId(): Int {
        return R.layout.comment_ui
    }

    override fun onDestroy() {
        adapter?.finish()
        super.onDestroy()
    }
}