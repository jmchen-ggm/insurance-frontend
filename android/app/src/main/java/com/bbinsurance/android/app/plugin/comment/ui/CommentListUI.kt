package com.bbinsurance.android.app.plugin.comment.ui

import android.content.Intent
import android.view.View
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.plugin.account.AccountCore
import com.bbinsurance.android.app.plugin.comment.CommentLogic
import com.bbinsurance.android.app.ui.BaseListActivity
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.item.BaseDataItem

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
            CommentLogic.goToAddCommentUI(this, intent)
        })

        adapter?.refreshCommentList(true)
    }

    override fun supportLoadMore(): Boolean {
        return true
    }

    override fun onLoadMore() {
        adapter?.refreshCommentList(false)
    }

    override fun getLayoutId(): Int {
        return R.layout.comment_ui
    }

    override fun onDestroy() {
        adapter?.finish()
        super.onDestroy()
    }

    override fun handleItemClick(view: View, dataItem: BaseDataItem, isHandle: Boolean): Boolean {
        var commentDataItem = dataItem as CommentDataItem
        commentDataItem.comment.ViewCount++
        var intent = Intent()
        intent.putExtra(UIConstants.CommentDetailUI.KeyComment, JSON.toJSONString(commentDataItem.comment))
        CommentLogic.goToCommentDetailUI(this, intent)
        return true
    }

    override fun onLoginSuccess() {
        adapter?.refreshCommentList(true)
    }
}