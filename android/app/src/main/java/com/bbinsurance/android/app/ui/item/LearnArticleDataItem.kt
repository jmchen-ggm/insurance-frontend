package com.bbinsurance.android.app.ui.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.entity.LearnArticleEntity
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 2017/10/27.
 */
class LearnArticleDataItem : BaseDataItem {

    lateinit var entity : LearnArticleEntity

    constructor(position : Int) : super(UIConstants.ListViewType.LearnArticle, position) {

    }

    override fun inflateView(context: Context, parent: ViewGroup?, itemView: View?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.learn_article_item_view, parent, false)
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
        holder.titleTV.text = entity.title
        holder.descTV.text = entity.desc
        holder.thumbIV.setImageURI(String.format("http://120.78.175.235:8081/img/articles/%d.png", entity.id))
        holder.timeTV.text = entity.time
    }

    class LearnArticleViewHolder : BaseViewHolder() {
        lateinit var thumbIV : SimpleDraweeView
        lateinit var titleTV : TextView
        lateinit var descTV : TextView
        lateinit var timeTV : TextView
    }
}