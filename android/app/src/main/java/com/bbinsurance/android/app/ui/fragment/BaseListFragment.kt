package com.bbinsurance.android.app.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.component.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 17/11/13.
 */
abstract class BaseListFragment : Fragment(), ListBaseUIComponent {

    private lateinit var lv : ListView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var convertView = inflater?.inflate(getLayoutId(), container, false)
        if (convertView != null) {
            lv = convertView.findViewById<ListView>(R.id.listview)
            var headView = getHeaderView()
            if (headView != null) {
                lv.addHeaderView(headView)
            }
            lv.adapter = getAdapter()
            lv.onItemClickListener = onItemClickListener
        }
        return convertView
    }

    abstract fun getLayoutId() : Int

    open fun getHeaderView() : View? {
        return null
    }

    abstract fun getAdapter() : BBBaseAdapter

    override fun getListView(): ListView {
        return lv
    }

    override fun getComponentContext(): Context {
        return context
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