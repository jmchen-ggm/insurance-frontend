package com.bbinsurance.android.app.plugin.insurance.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.alibaba.fastjson.JSON
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
        groupList.clear()
        var ageGroup = InsuranceCompareGroup()
        ageGroup.name = context.getString(R.string.insurance_age_range_compare)
        var ageChild = InsuranceCompareChild()
        ageChild.left = context.getString(R.string.insurance_age_range_number, leftDetail.AgeFrom, leftDetail.AgeTo)
        ageChild.right = context.getString(R.string.insurance_age_range_number, rightDetail.AgeFrom, rightDetail.AgeTo)
        ageGroup.children.add(ageChild)
        groupList.add(ageGroup)

        var compensationGroup = InsuranceCompareGroup()
        compensationGroup.name = context.getString(R.string.insurance_compensation_compare)
        var compensationChild = InsuranceCompareChild()
        compensationChild.left = context.getString(R.string.insurance_compensation_number, leftDetail.AnnualCompensation)
        compensationChild.right = context.getString(R.string.insurance_compensation_number, rightDetail.AnnualCompensation)
        compensationGroup.children.add(compensationChild)
        groupList.add(compensationGroup)

        var premiumGroup = InsuranceCompareGroup()
        premiumGroup.name = context.getString(R.string.insurance_premium_compare)
        var premiumChild = InsuranceCompareChild()
        premiumChild.left = String.format("%d", leftDetail.AnnualPremium)
        premiumChild.right = String.format("%d", rightDetail.AnnualPremium)
        premiumGroup.children.add(premiumChild)
        groupList.add(premiumGroup)

        var leftDetailObj = JSON.parseObject(leftDetail.DetailData)
        var rightDetailObj = JSON.parseObject(rightDetail.DetailData)

        groupList.add(parseCompareGroup(leftDetailObj.getJSONObject("score"), rightDetailObj.getJSONObject("score")))
        groupList.add(parseCompareGroup(leftDetailObj.getJSONObject("nonSeriousProtected"), rightDetailObj.getJSONObject("nonSeriousProtected")))
        groupList.add(parseCompareGroup(leftDetailObj.getJSONObject("seriousProtected"), rightDetailObj.getJSONObject("seriousProtected")))
        groupList.add(parseCompareGroup(leftDetailObj.getJSONObject("premium"), rightDetailObj.getJSONObject("premium")))

        notifyDataSetChanged()
    }

    fun clearData() {
        groupList.clear()
        notifyDataSetChanged()
    }

    private fun parseCompareGroup(leftObj: JSONObject, rightObj : JSONObject): InsuranceCompareGroup {
        var group = InsuranceCompareGroup()
        group.name = leftObj.getString("name")
        var leftValueArray = leftObj.getJSONArray("value")
        var rightValueArray = rightObj.getJSONArray("value")
        for (i in leftValueArray.indices) {
            var child = InsuranceCompareChild()
            var leftValueObj = leftValueArray.getJSONObject(i)
            var rightValueObj = rightValueArray.getJSONObject(i)
            child.name = leftValueObj.getString("name")
            child.left = leftValueObj.getString("value")
            child.right = rightValueObj.getString("value")
            group.children.add(child)
        }
        return group
    }
}