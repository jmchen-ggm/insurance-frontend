package com.bbinsurance.android.app.ui

import android.content.Context
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ListView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.component.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 17/11/13.
 */
abstract class BaseListActivity : BaseActivity(), ListBaseUIComponent {

    private lateinit var lv : ListView
    private lateinit var refreshView : View

    override fun initView() {
        super.initView()
        lv = findViewById<ListView>(R.id.listview)
        var headView = getHeaderView()
        if (headView != null) {
            lv.addHeaderView(headView)
        }
        var footerView = layoutInflater.inflate(R.layout.list_refresh_footer, null)
        refreshView = footerView.findViewById(R.id.refresh_footer)
        onLoadMoreFinish()
        lv.addFooterView(footerView)

        lv.adapter = getAdapter()
        lv.setOnScrollListener(onScrollListener)
        lv.onItemClickListener = onItemClickListener
    }

    open fun getHeaderView() : View? {
        return null
    }

    abstract fun getAdapter() : BBBaseAdapter

    override fun getListView(): ListView {
        return lv
    }

    override fun getComponentContext(): Context {
        return this
    }

    override fun onItemClick(view: View, dataItem: BaseDataItem, isHandled: Boolean) {

    }

    private var onItemClickListener : AdapterView.OnItemClickListener = object : AdapterView.OnItemClickListener {

        override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
            var dataItem = getAdapter().getItem(position)
            var handleResult = handleItemClick(view, dataItem, false)
            getAdapter().handleItemClick(view, dataItem, handleResult)
        }
    }

    private var lastVisibleItem = -1
    private var totalItemCount = 0
    private var loading = false

    private var onScrollListener = object : AbsListView.OnScrollListener {

        override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
            if (supportLoadMore()) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastVisibleItem == totalItemCount && !loading) {
                    refreshView.visibility = View.VISIBLE
                    loading = true
                    onLoadMore()
                } else {
                    refreshView.visibility = View.GONE
                }
            }
        }

        override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, itemCount: Int) {
            lastVisibleItem = firstVisibleItem + visibleItemCount;
            totalItemCount = itemCount;
        }
    }

    override fun onLoadMoreFinish() {
        loading = false
        refreshView.visibility = View.GONE
    }

    open fun onLoadMore() {
    }

    open fun supportLoadMore() : Boolean {
        return false;
    }

    open fun handleItemClick(view : View, dataItem : BaseDataItem, isHandle : Boolean) : Boolean {
        return false
    }
}