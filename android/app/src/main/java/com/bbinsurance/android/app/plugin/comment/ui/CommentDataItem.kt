package com.bbinsurance.android.app.plugin.comment.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.ContactEntity
import com.bbinsurance.android.app.protocol.BBComment
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.widget.FrescoUtil
import com.bbinsurance.android.lib.util.TimeUtil
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentDataItem : BaseDataItem {

    constructor(position: Int) : super(UIConstants.ListViewType.Comment, position) {

    }

    lateinit var comment: BBComment

    override fun inflateView(context: Context, parent: ViewGroup?, itemView: View?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.comment_item_view, parent, false)
        var viewHolder = CommentViewHolder()
        viewHolder.thumbIV = view?.findViewById(R.id.thumb_iv)
        viewHolder.nickNameTV = view?.findViewById(R.id.nickname_tv)
        viewHolder.timeTV = view?.findViewById(R.id.time_tv)
        viewHolder.scoreTV = view?.findViewById(R.id.score_tv)
        viewHolder.viewTV = view?.findViewById(R.id.view_tv)
        viewHolder.upTV = view?.findViewById(R.id.up_tv)
        viewHolder.replyTV = view?.findViewById(R.id.reply_tv)
        viewHolder.likeIV = view?.findViewById(R.id.like_iv)
        viewHolder.likeTV = view?.findViewById(R.id.like_tv)
        viewHolder.commentLayout = view?.findViewById(R.id.comment_layout)
        viewHolder.contentTV = view?.findViewById(R.id.content_tv)
        viewHolder.starsIV[0] = view?.findViewById(R.id.star_1_iv)
        viewHolder.starsIV[1] = view?.findViewById(R.id.star_2_iv)
        viewHolder.starsIV[2] = view?.findViewById(R.id.star_3_iv)
        viewHolder.starsIV[3] = view?.findViewById(R.id.star_4_iv)
        viewHolder.starsIV[4] = view?.findViewById(R.id.star_5_iv)
        view.tag = (viewHolder)
        return view
    }

    var contactEntity : ContactEntity ? = null
    override fun fillView(context: Context, viewHolder: BaseViewHolder) {
        var commentViewHolder = viewHolder as CommentViewHolder
        if (contactEntity == null) {
            contactEntity = BBCore.Instance.accountCore.syncService.getContact(comment.Uin)
        }
        commentViewHolder.thumbIV?.hierarchy = FrescoUtil.getCircleHierarchy()
        if (contactEntity == null) {
            commentViewHolder.nickNameTV?.text = String.format("%d", comment.Uin)
            commentViewHolder.thumbIV?.setImageURI("res://sadf/" + R.drawable.tab_my_icon)
        } else {
            commentViewHolder.nickNameTV?.text = contactEntity?.Nickname
            commentViewHolder.thumbIV?.setImageURI(BBCore.Instance.accountCore.getContactThumbUrl(contactEntity))
        }
        var scoreIndex = comment.TotalScore / 20
        for (index : Int in viewHolder.starsIV.indices) {
            if (index < scoreIndex) {
                viewHolder.starsIV[index]?.setImageResource(R.drawable.star_yellow)
            } else {
                viewHolder.starsIV[index]?.setImageResource(R.drawable.star_gray)
            }
        }
        commentViewHolder.scoreTV?.text = context.getString(R.string.comment_star_info, scoreIndex)
        commentViewHolder.timeTV?.text = TimeUtil.formatTimeInList(context, comment.Timestamp)
        commentViewHolder.contentTV?.text = comment.Content.trim()
        commentViewHolder.viewTV?.text = context.getString(R.string.comment_view_info, comment.ViewCount)
        commentViewHolder.upTV?.text = context.getString(R.string.comment_up_info, comment.UpCount)
        commentViewHolder.replyTV?.text = context.getString(R.string.comment_reply_info, comment.ReplyCount)
    }

    class CommentViewHolder : BaseViewHolder() {
        var thumbIV: SimpleDraweeView? = null
        var nickNameTV: TextView? = null
        var contentTV: TextView? = null
        var viewTV : TextView ? = null
        var upTV : TextView ? = null
        var replyTV : TextView ? = null
        var likeIV : ImageView ? = null
        var likeTV : TextView ? = null
        var commentLayout : View ? = null
        var timeTV: TextView? = null
        var scoreTV: TextView? = null
        var starsIV = arrayOfNulls<ImageView?>(5)
    }
}