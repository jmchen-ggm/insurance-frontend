package com.bbinsurance.android.app.plugin.company.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.protocol.BBCompany
import com.bbinsurance.android.app.protocol.BBInsuranceType
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 18/1/6.
 */
class InsuranceTypeDataItem : BaseDataItem {

    constructor(position: Int) : super(UIConstants.ListViewType.Company, position)

    lateinit var insuranceType : BBInsuranceType

    override fun inflateView(context: Context, parent: ViewGroup?, itemView: View?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.insurance_type_item_view, parent, false)
        var viewHolder = CompanyViewHolder()
        viewHolder.thumbIV = view?.findViewById(R.id.thumb_iv)
        viewHolder.nameTV = view?.findViewById(R.id.name_tv)
        viewHolder.descTV = view?.findViewById(R.id.desc_tv)
        view.tag = (viewHolder)
        return view
    }

    override fun fillView(context: Context, viewHolder: BaseViewHolder) {
        var companyViewHolder = viewHolder as CompanyViewHolder
        companyViewHolder.nameTV?.text = insuranceType.Name
        companyViewHolder.descTV?.text = insuranceType.Desc
        companyViewHolder.thumbIV?.setImageURI(ProtocolConstants.URL.FileServer + insuranceType.ThumbUrl)
    }

    class CompanyViewHolder : BaseViewHolder() {
        var thumbIV: SimpleDraweeView? = null
        var nameTV: TextView? = null
        var descTV: TextView? = null
    }
}