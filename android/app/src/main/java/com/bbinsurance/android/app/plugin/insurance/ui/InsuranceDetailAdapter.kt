package com.bbinsurance.android.app.plugin.insurance.ui;

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.alibaba.fastjson.JSONObject
import com.bbinsurance.android.app.plugin.insurance.model.InsuranceDetailChild
import com.bbinsurance.android.app.plugin.insurance.model.InsuranceDetailChildDataItem
import com.bbinsurance.android.app.plugin.insurance.model.InsuranceDetailGroup
import com.bbinsurance.android.app.plugin.insurance.model.InsuranceDetailGroupDataItem

/**
 * Created by jiaminchen on 18/2/3.
 */

class InsuranceDetailAdapter : BaseExpandableListAdapter {

    var context : Context
    constructor(context : Context) : super() {
        this.context = context
    }

    override fun getGroup(groupPosition: Int): InsuranceDetailGroup {
        return groupList[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var dataItem = InsuranceDetailGroupDataItem()
        dataItem.group = getGroup(groupPosition);
        dataItem.isExpand = isExpanded
        var itemView = convertView
        if (itemView == null) {
            itemView = dataItem.inflateView(context, parent)
        }
        var holder = itemView.tag as InsuranceDetailGroupDataItem.Holder
        dataItem.fillView(holder)
        return itemView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return groupList[groupPosition].children.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): InsuranceDetailChild {
        return groupList[groupPosition].children[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return 0
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var dataItem = InsuranceDetailChildDataItem()
        dataItem.child = getChild(groupPosition, childPosition);
        var itemView = convertView
        if (itemView == null) {
            itemView = dataItem.inflateView(context, parent)
        }
        var holder = itemView.tag as InsuranceDetailChildDataItem.Holder
        dataItem.fillView(holder)
        return itemView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return 0
    }

    override fun getGroupCount(): Int {
        return groupList.size
    }

    private var groupList = ArrayList<InsuranceDetailGroup>()
    fun setData(detailData: JSONObject) {
        groupList.add(parseGroup(detailData.getJSONObject("score")))
        groupList.add(parseGroup(detailData.getJSONObject("nonSeriousProtected")))
        groupList.add(parseGroup(detailData.getJSONObject("seriousProtected")))
        groupList.add(parseGroup(detailData.getJSONObject("premium")))
        notifyDataSetChanged()

    }

    private fun parseGroup(obj: JSONObject): InsuranceDetailGroup {
        var group = InsuranceDetailGroup()
        group.name = obj.getString("name")
        var valueArray = obj.getJSONArray("value")
        for (i in valueArray.indices) {
            var valueObj = valueArray.getJSONObject(i)
            var child = InsuranceDetailChild()
            child.name = valueObj.getString("name")
            child.desc = valueObj.getString("value")
            group.children.add(child)
        }
        return group
    }
}
