package com.bbinsurance.android.app.plugin.insurance.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.protocol.BBInsurance
import com.bbinsurance.android.app.ui.adapter.InsuranceAdapter
import com.bbinsurance.android.app.ui.item.BaseDataItem
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
        viewHolder.companyTV = view.findViewById(R.id.company_tv)
        view.tag = (viewHolder)
        return view
    }

    override fun fillView(context: Context, viewHolder: BaseViewHolder) {
        var holder = viewHolder as RecommendInsuranceViewHolder
        holder.titleTV.text = entity.Name
        if (Util.isNullOrNil(entity.Desc)) {
            if (entity.AnnualCompensation != 0) {
                holder.descTV.text = Util.nullAs(entity.Desc, context.getString(R.string.annal_compensation_desc, entity.AnnualCompensation / 10000))
            } else {
                holder.descTV.text = Util.nullAs(entity.Desc, "")
            }
        } else {
            holder.descTV.text = Util.nullAs(entity.Desc, "")
        }
        holder.companyTV.text = Util.nullAs(entity.CompanyName, "")
        holder.thumbIV.hierarchy = InsuranceAdapter.getCornerRoundHierarchy()
        holder.thumbIV.setImageURI(entity.ThumbUrl)
    }

    override fun fillData(context: Context, viewHolder: BaseViewHolder) {

    }

    lateinit var entity: BBInsurance

    class RecommendInsuranceViewHolder : BaseViewHolder() {
        lateinit var thumbIV: SimpleDraweeView
        lateinit var titleTV : TextView
        lateinit var descTV: TextView
        lateinit var companyTV : TextView
    }
}