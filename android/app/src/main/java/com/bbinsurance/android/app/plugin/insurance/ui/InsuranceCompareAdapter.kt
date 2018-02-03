package com.bbinsurance.android.app.plugin.insurance.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.alibaba.fastjson.JSONObject
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.plugin.insurance.model.InsuranceCompareChild
import com.bbinsurance.android.app.plugin.insurance.model.InsuranceCompareChildDataItem
import com.bbinsurance.android.app.plugin.insurance.model.InsuranceCompareGroup
import com.bbinsurance.android.app.plugin.insurance.model.InsuranceCompareGroupDataItem
import com.bbinsurance.android.app.protocol.BBInsuranceDetail

/**
 * Created by jiaminchen on 18/2/3.
 */

class InsuranceCompareAdapter : BaseExpandableListAdapter {

    var context : Context
    constructor(context : Context) : super() {
        this.context = context
    }

    override fun getGroup(groupPosition: Int): InsuranceCompareGroup {
        return groupList[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var dataItem = InsuranceCompareGroupDataItem()
        dataItem.group = getGroup(groupPosition);
        dataItem.isExpand = isExpanded
        var itemView = convertView
        if (itemView == null) {
            itemView = dataItem.inflateView(context, parent)
        }
        var holder = itemView.tag as InsuranceCompareGroupDataItem.Holder
        dataItem.fillView(holder)
        return itemView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return groupList[groupPosition].children.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): InsuranceCompareChild {
        return groupList[groupPosition].children[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return 0
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var dataItem = InsuranceCompareChildDataItem()
        dataItem.child = getChild(groupPosition, childPosition);
        var itemView = convertView
        if (itemView == null) {
            itemView = dataItem.inflateView(context, parent)
        }
        var holder = itemView.tag as InsuranceCompareChildDataItem.Holder
        dataItem.fillView(holder)
        return itemView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return 0
    }

    override fun getGroupCount(): Int {
        return groupList.size
    }

    private var groupList = ArrayList<InsuranceCompareGroup>()
    fun setData(leftDetail : BBInsuranceDetail, rightDetail : BBInsuranceDetail) {
        clearData()
        var ageGroup = InsuranceCompareGroup()
        ageGroup.name = context.getString(R.string.insurance_age_range_compare)
        var ageChild = InsuranceCompareChild()
        ageChild.left = context.getString(R.string.insurance_age_range_number, leftDetail.AgeFrom, leftDetail.AgeTo)
        ageChild.right = context.getString(R.string.insurance_age_range_number, rightDetail.AgeFrom, rightDetail.AgeTo)
        ageGroup.children.add(ageChild)
        groupList.add(ageGroup)
        notifyDataSetChanged()
    }

    fun clearData() {
        groupList.clear()
    }

    private fun parseGroup(obj: JSONObject): InsuranceCompareGroup {
        var group = InsuranceCompareGroup()
        group.name = obj.getString("name")
        var valueArray = obj.getJSONArray("value")
        for (i in valueArray.indices) {
            var valueObj = valueArray.getJSONObject(i)
            var child = InsuranceCompareChild()
            child.name = valueObj.getString("name")
//            child.desc = valueObj.getString("value")
            group.children.add(child)
        }
        return group
    }
}