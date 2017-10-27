package com.bbinsurance.android.app.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 2017/10/27.
 */
abstract class BaseListViewUI : BaseActivity(), ListBaseUIComponent {

    private lateinit var lv : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        super.initView()
        lv = findViewById(R.id.listview)
        var headerView = getHeaderView()
        if (headerView != null) {
            lv.addHeaderView(headerView)
        }
        lv.adapter = getAdapter()
        lv.onItemClickListener = onItemClickListener
    }

    open fun getHeaderView() : View? {
        return null
    }

    abstract fun getAdapter() : BBBaseAdapter

    override fun getListView(): ListView {
        return lv
    }

    override fun getContext(): Context {
        return this
    }

    override fun onItemClick(view: View, dataItem: BaseDataItem, isHandled: Boolean) {
    }

    private var onItemClickListener : AdapterView.OnItemClickListener = object : AdapterView.OnItemClickListener {

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            var dataItem = getAdapter().getItem(position)
            getAdapter().handleItemClick(view!!, dataItem, false)
        }
    }
}