package com.bbinsurance.android.app.plugin.comment.ui

import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.protocol.BBCommentReply
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.component.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 18/1/13.
 */
class CommentDetailAdapter : BBBaseAdapter {

    var commentReplyList  = ArrayList<BBCommentReply>()

    override fun createDataItem(position: Int): BaseDataItem {
        var dataItem = CommentReplyDataItem(position)
        dataItem.commentReply = commentReplyList[position]
        return dataItem
    }

    override fun getCount(): Int {
        return commentReplyList.size
    }

    constructor(component: ListBaseUIComponent) : super(component) {
    }
}