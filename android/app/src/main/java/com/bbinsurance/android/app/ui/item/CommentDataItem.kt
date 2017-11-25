package com.bbinsurance.android.app.ui.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.db.entity.CommentEntity
import com.bbinsurance.android.lib.util.TimeUtil

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentDataItem : BaseDataItem {

    constructor(position: Int) : super(UIConstants.ListViewType.Comment, position) {

    }

    lateinit var comment: CommentEntity

    override fun inflateView(context: Context, parent: ViewGroup?, itemView: View?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.comment_item_view, parent, false)
        var viewHolder = CommentDataItem.CommentViewHolder()
        viewHolder.thumbIV = view?.findViewById(R.id.thumb_iv)
        viewHolder.nickNameTV = view?.findViewById(R.id.nickname_tv)
        viewHolder.timeTV = view?.findViewById(R.id.time_tv)
        viewHolder.scoreTV = view?.findViewById(R.id.score_tv)
        viewHolder.contentTV = view?.findViewById(R.id.content_tv)
        view.tag = (viewHolder)
        return view
    }

    override fun fillView(context: Context, viewHolder: BaseViewHolder) {
        var commentViewHolder = viewHolder as CommentViewHolder
        commentViewHolder.nickNameTV?.text = String.format("%d", comment.Uin)
        commentViewHolder.scoreTV?.text = (String.format("%d", comment.TotalScore))
        commentViewHolder.timeTV?.text = TimeUtil.formatTime(comment.Timestamp * 1000L)
        commentViewHolder.contentTV?.text = comment.Content
    }

    class CommentViewHolder : BaseViewHolder() {
        var thumbIV: ImageView? = null
        var nickNameTV: TextView? = null
        var timeTV: TextView? = null
        var scoreTV: TextView? = null
        var contentTV: TextView? = null
    }
}