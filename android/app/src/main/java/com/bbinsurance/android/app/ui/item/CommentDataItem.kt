package com.bbinsurance.android.app.ui.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentDataItem : BaseDataItem {

    constructor(position: Int) : super(UIConstants.ListViewType.Comment, position) {

    }

    override fun inflateView(context: Context, parent: ViewGroup?, itemView: View?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.comment_item_view, parent, false)
        var viewHolder = CommentDataItem.CommentViewHolder()
        view.tag = (viewHolder)
        return view
    }

    override fun fillView(context: Context, viewHolder: BaseViewHolder) {
    }

    class CommentViewHolder : BaseViewHolder() {
    }
}