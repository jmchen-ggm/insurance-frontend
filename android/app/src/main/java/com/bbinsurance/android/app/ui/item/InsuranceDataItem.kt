package com.bbinsurance.android.app.ui.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.protocol.BBInsurance
import com.bbinsurance.android.app.ui.adapter.InsuranceAdapter
import com.bbinsurance.android.lib.Util
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 2017/10/25.
 */
class InsuranceDataItem : BaseDataItem {

    constructor(position : Int) : super(UIConstants.ListViewType.RecommendInsurance, position) {

    }

    override fun inflateView(context: Context, parent: ViewGroup?, itemView: View?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.insurance_item_view, parent, false)
        var viewHolder = RecommendInsuranceViewHolder()
        viewHolder.titleTV = view.findViewById(R.id.title_tv)
        viewHolder.descTV = view.findViewById(R.id.desc_tv)
        viewHolder.thumbIV = view.findViewById(R.id.thumb_iv)
        view.tag = (viewHolder)
        return view
    }

    override fun fillView(context: Context, viewHolder: BaseViewHolder) {
        var holder = viewHolder as RecommendInsuranceViewHolder
        holder.titleTV.text = entity.NameZHCN
        holder.descTV.text = Util.nullAs(entity.Desc, "")
        holder.thumbIV.hierarchy = InsuranceAdapter.getCornerRoundHierarchy()
        holder.thumbIV.setImageURI(ProtocolConstants.URL.FileServer + entity.ThumbUrl)
    }

    override fun fillData(context: Context, viewHolder: BaseViewHolder) {

    }

    lateinit var entity: BBInsurance

    class RecommendInsuranceViewHolder : BaseViewHolder() {
        lateinit var thumbIV: SimpleDraweeView
        lateinit var titleTV : TextView
        lateinit var descTV: TextView
    }
}