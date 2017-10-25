package com.bbinsurance.android.app.ui.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.entity.InsuranceEntity
import com.bbinsurance.android.lib.Util

/**
 * Created by jiaminchen on 2017/10/25.
 */
class RecommendInsuranceDataItem : BaseDataItem {

    constructor(position : Int) : super(UIConstants.ListViewType.RecommendInsurance, position) {

    }

    override fun inflateView(context: Context, parent: ViewGroup?, itemView: View?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.recommned_insurance_item_view, parent, false)
        var viewHolder = RecommendInsuranceViewHolder()
        viewHolder.titleTV = view.findViewById(R.id.title_tv)
        viewHolder.dividerView = view.findViewById(R.id.divider_view)
        viewHolder.additionTV = view.findViewById(R.id.addition_tv)
        viewHolder.scoreTV = view.findViewById(R.id.score_tv)
        view.tag = (viewHolder)
        return view
    }

    override fun fillView(context: Context, viewHolder: BaseViewHolder) {
        var holder = viewHolder as RecommendInsuranceViewHolder
        holder.titleTV.text = insuranceEntity.name
        holder.additionTV.text = Util.nullAs(insuranceEntity.addition, "")
        holder.scoreTV.text = context.getString(R.string.score_formatter, insuranceEntity.score)
        holder.dividerView.visibility = View.VISIBLE
    }

    override fun fillData(context: Context, viewHolder: BaseViewHolder) {
    }

    lateinit var insuranceEntity : InsuranceEntity

    class RecommendInsuranceViewHolder : BaseViewHolder() {
        lateinit var dividerView : View
        lateinit var titleTV : TextView
        lateinit var additionTV : TextView
        lateinit var scoreTV : TextView
    }
}