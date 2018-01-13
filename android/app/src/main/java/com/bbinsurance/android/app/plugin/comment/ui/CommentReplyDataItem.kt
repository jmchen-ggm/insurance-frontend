package com.bbinsurance.android.app.plugin.comment.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.ContactEntity
import com.bbinsurance.android.app.protocol.BBCommentReply
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.widget.FrescoUtil
import com.bbinsurance.android.lib.util.TimeUtil
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 18/1/13.
 */
class CommentReplyDataItem : BaseDataItem {

    constructor(position: Int) : super(UIConstants.ListViewType.Comment, position) {

    }

    lateinit var commentReply : BBCommentReply

    override fun inflateView(context: Context, parent: ViewGroup?, itemView: View?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_view, parent, false)
        var viewHolder = ReplyCommentViewHolder()
        viewHolder.avatarTV = view.findViewById(R.id.avatar_iv)
        viewHolder.nickNameTV = view.findViewById(R.id.nickname_tv)
        viewHolder.contentTV = view.findViewById(R.id.content_tv)
        viewHolder.timeTV = view.findViewById(R.id.time_tv)
        view.setTag(viewHolder)
        return view
    }

    var contactEntity: ContactEntity? = null
    var replyContactEntity : ContactEntity ? = null
    override fun fillView(context: Context, viewHolder: BaseViewHolder) {
        var holder = viewHolder as ReplyCommentViewHolder
        if (contactEntity == null) {
            contactEntity = BBCore.Instance.accountCore.syncService.getContact(commentReply.Uin)
        }
        holder.avatarTV?.hierarchy = FrescoUtil.getCircleHierarchy()
        if (contactEntity == null) {
            holder.nickNameTV?.text = String.format("%d", commentReply.Uin)
            holder.avatarTV?.setImageURI("res://sadf/" + R.drawable.tab_my_icon)
        } else {
            holder.nickNameTV?.text = contactEntity?.Nickname
            holder.avatarTV?.setImageURI(BBCore.Instance.accountCore.getContactThumbUrl(contactEntity))
        }
        if (commentReply.ReplyUin != 0L) {
            if (replyContactEntity == null) {
                replyContactEntity = BBCore.Instance.accountCore.syncService.getContact(commentReply.ReplyUin)
            }
        }

        var prefix = ""
        if (replyContactEntity != null) {
            prefix = context.getString(R.string.comment_reply_uin_prefix, replyContactEntity?.Nickname)
        }
        holder.contentTV?.text = prefix + commentReply.Content
        holder.timeTV?.text = TimeUtil.formatTimeInList(context, commentReply.Timestamp)
    }


    class ReplyCommentViewHolder : BaseViewHolder() {
        var avatarTV: SimpleDraweeView? = null
        var nickNameTV: TextView? = null
        var contentTV: TextView? = null
        var timeTV: TextView? = null
    }
}