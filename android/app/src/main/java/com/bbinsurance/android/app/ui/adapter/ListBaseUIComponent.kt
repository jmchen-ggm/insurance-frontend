package com.bbinsurance.android.app.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ListView
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 17/10/25.
 */
interface ListBaseUIComponent {

    fun getContext() : Context

    fun onItemClick(view : View, dataItem : BaseDataItem, isHandled : Boolean)

    fun getListView() : ListView
}