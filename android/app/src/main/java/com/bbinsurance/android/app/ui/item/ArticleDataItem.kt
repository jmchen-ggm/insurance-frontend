package com.bbinsurance.android.app.ui.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.protocol.BBArticle
import com.bbinsurance.android.app.ui.adapter.ArticleAdapter
import com.facebook.drawee.view.SimpleDraweeView


/**
 * Created by jiaminchen on 2017/10/27.
 */
class ArticleDataItem : BaseDataItem {

    lateinit var entity : BBArticle

    constructor(position : Int) : super(UIConstants.ListViewType.LearnArticle, position) {

    }

    override fun inflateView(context: Context, parent: ViewGroup?, itemView: View?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.article_item_view, parent, false)
        var viewHolder = LearnArticleViewHolder()
        viewHolder.titleTV = view.findViewById(R.id.title_tv)
        viewHolder.descTV = view.findViewById(R.id.desc_tv)
        viewHolder.thumbIV = view.findViewById(R.id.thumb_iv)
        viewHolder.timeTV = view.findViewById(R.id.time_tv)
        view.tag = (viewHolder)
        return view
    }

    override fun fillView(context: Context, viewHolder: BaseViewHolder) {
        var holder = viewHolder as LearnArticleViewHolder
        holder.titleTV.text = entity.Title
        holder.descTV.text = entity.Desc
        holder.thumbIV.hierarchy = ArticleAdapter.getCornerRoundHierarchy()
        holder.thumbIV.setImageURI(ProtocolConstants.URL.FileServer + entity.ThumbUrl)

        holder.timeTV.text = entity.Date
    }

    class LearnArticleViewHolder : BaseViewHolder() {
        lateinit var thumbIV : SimpleDraweeView
        lateinit var titleTV : TextView
        lateinit var descTV : TextView
        lateinit var timeTV : TextView
    }
}