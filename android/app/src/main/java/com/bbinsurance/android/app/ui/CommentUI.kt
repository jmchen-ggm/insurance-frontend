package com.bbinsurance.android.app.ui

import android.view.View
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.CommentAdapter

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentUI : BaseListActivity() {

    var adapter: CommentAdapter? = null
    override fun getAdapter(): BBBaseAdapter {
        if (adapter == null) {
            adapter = CommentAdapter(this)
        }
        return adapter!!
    }

    override fun getTitleId(): Int {
        return R.string.comment
    }

    override fun getBackBtnVisible(): Boolean {
        return true
    }

    override fun getBackBtnListener(): View.OnClickListener? {
        return View.OnClickListener {
            finish()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.comment_ui
    }
}