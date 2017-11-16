package com.bbinsurance.android.app.ui

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 17/11/13.
 */
abstract class BaseListActivity : BaseActivity(), ListBaseUIComponent {

    private lateinit var lv : ListView

    override fun initView() {
        super.initView()
        lv = findViewById<ListView>(R.id.listview)
        var headView = getHeaderView()
        if (headView != null) {
            lv.addHeaderView(headView)
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

    override fun getComponentContext(): Context {
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