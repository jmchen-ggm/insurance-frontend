package com.bbinsurance.android.app.ui.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.lib.log.Log

/**
 * Created by jiaminchen on 17/10/25.
 */
abstract class BBBaseAdapter : BaseAdapter {
    private val TAG = "BB.BBBaseAdapter"
    private var uiComponent : ListBaseUIComponent ? = null

    constructor(uiComponent: ListBaseUIComponent) : super() {
        this.uiComponent = uiComponent
    }

    private var itemCache : SparseArray<BaseDataItem> = SparseArray()
    private var count = 0;

    override fun getView(position: Int, convertView : View?, parent: ViewGroup?): View {
        val dataItem : BaseDataItem ? = getItem(position)
        var returnView = convertView;
        if (returnView == null) {
            returnView = dataItem!!.inflateView(uiComponent!!.getContext(),
                    parent, returnView)
        }
        var viewHolder = returnView.getTag() as BaseDataItem.BaseViewHolder
        // 如果已经填充过数据，就不需要再填充了
        if (!dataItem!!.isFillData) {
            dataItem!!.fillData(uiComponent!!.getContext(), viewHolder)
            dataItem!!.isFillData = true
        }
        dataItem!!.fillView(uiComponent!!.getContext(), viewHolder)
        return returnView
    }

    override fun getItem(position: Int): BaseDataItem? {
        if (itemCache.indexOfKey(position) >= 0) {
            return itemCache.get(position)
        } else {
            var baseDataItem: BaseDataItem? = null
            if (position >= 0 && position < getCount()) {
                baseDataItem = createDataItem(position)
            }
            if (baseDataItem == null) {
                Log.e(TAG, "getItem Occur error ! position = %d | count=%d", position, getCount())
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
}