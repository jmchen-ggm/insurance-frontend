package com.bbinsurance.android.app.ui.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.item.EmptyDataItem
import com.bbinsurance.android.lib.log.BBLog

/**
 * Created by jiaminchen on 17/10/25.
 */
abstract class BBBaseAdapter : BaseAdapter {
    private val TAG = "BB.BBBaseAdapter"
    protected var uiComponent : ListBaseUIComponent

    constructor(uiComponent: ListBaseUIComponent) : super() {
        this.uiComponent = uiComponent
    }

    private var itemCache : SparseArray<BaseDataItem> = SparseArray()
    private var count = 0;

    override fun getView(position: Int, convertView : View?, parent: ViewGroup?): View {
        val dataItem : BaseDataItem = getItem(position)
        var returnView = convertView;
        if (returnView == null) {
            returnView = dataItem.inflateView(uiComponent.getComponentContext(),
                    parent, returnView)
        }
        var viewHolder = returnView.tag as BaseDataItem.BaseViewHolder
        // 如果已经填充过数据，就不需要再填充了
        if (!dataItem.isFillData) {
            dataItem.fillData(uiComponent.getComponentContext(), viewHolder)
            dataItem.isFillData = true
        }
        dataItem.fillView(uiComponent.getComponentContext(), viewHolder)
        return returnView
    }

    override fun getItem(position: Int): BaseDataItem {
        if (itemCache.indexOfKey(position) >= 0) {
            return itemCache.get(position)
        } else {
            var baseDataItem: BaseDataItem? = null
            if (position >= 0 && position < getCount()) {
                baseDataItem = createDataItem(position)
            }
            if (baseDataItem == null) {
                baseDataItem = EmptyDataItem(position)
                BBLog.e(TAG, "getItem Occur error ! position = %d | count=%d", position, getCount())
            } else {
                itemCache.put(position, baseDataItem)
            }
            return baseDataItem
        }
    }

    abstract fun createDataItem(position : Int) : BaseDataItem

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return this.count
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun getViewTypeCount(): Int {
        return UIConstants.ListViewType.Count
    }

    open fun handleItemClick(view : View?, dataItem : BaseDataItem, isHandle : Boolean) {

    }

    open fun finish() {

    }

    open fun clearCache() {
        itemCache.clear()
    }
}