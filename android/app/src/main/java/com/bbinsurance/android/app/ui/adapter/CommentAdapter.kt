package com.bbinsurance.android.app.ui.adapter

import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.item.CommentDataItem

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentAdapter : BBBaseAdapter {

    constructor(component: ListBaseUIComponent) : super(component) {

    }

    override fun createDataItem(position: Int): BaseDataItem {
        return CommentDataItem(position)
    }
}