package com.bbinsurance.android.app.ui.component

import android.view.View
import android.widget.ListView
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 17/10/25.
 */
interface ListBaseUIComponent : BaseUIComponent {

    fun onItemClick(view : View, dataItem : BaseDataItem, isHandled : Boolean)

    fun getListView() : ListView
}