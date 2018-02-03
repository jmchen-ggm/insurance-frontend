package com.bbinsurance.android.app.plugin.insurance.ui;

import android.database.DataSetObserver
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter

/**
 * Created by jiaminchen on 18/2/3.
 */

class InsuranceDetailAdapter : ExpandableListAdapter {

    override fun getChildrenCount(groupPosition: Int): Int {
        return 0
    }

    override fun getGroup(groupPosition: Int): Any {
        return Object()
    }

    override fun onGroupCollapsed(groupPosition: Int) {
    }

    override fun isEmpty(): Boolean {
        return true
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return Object()
    }

    override fun onGroupExpanded(groupPosition: Int) {
    }

    override fun getCombinedChildId(groupPosition: Long, childPosition: Long): Long {
        return 0L
    }

    override fun getGroupId(groupPosition: Int): Long {
        return 0L
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        return convertView!!
    }

    override fun areAllItemsEnabled(): Boolean {
        return true
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return 0L
    }

    override fun getCombinedGroupId(groupPosition: Long): Long {
        return 0L
    }

    override fun getGroupView(groupPosition: Int, isLastGroup: Boolean, convertView: View?, parent: ViewGroup?): View {
        return convertView!!
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
    }

    override fun getGroupCount(): Int {
        return 0
    }
}
