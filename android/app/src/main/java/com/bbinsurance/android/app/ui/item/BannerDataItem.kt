package com.bbinsurance.android.app.ui.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.protocol.BBInsurance
import com.bbinsurance.android.app.ui.adapter.InsuranceAdapter
import com.bbinsurance.android.lib.Util
import com.bigkoo.convenientbanner.holder.Holder
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 17/12/24.
 */
class BannerDataItem : Holder<BBInsurance> {
    override fun updateUI(context: Context?, position: Int, entity: BBInsurance) {
        titleTV.text = entity.Name
        descTV.text = Util.nullAs(entity.Desc, "")
        companyTV.text = Util.nullAs(entity.CompanyName, "")
        thumbIV.hierarchy = InsuranceAdapter.getCornerRoundHierarchy()
        thumbIV.setImageURI(entity.ThumbUrl)
    }

    lateinit var thumbIV: SimpleDraweeView
    lateinit var titleTV : TextView
    lateinit var descTV: TextView
    lateinit var companyTV : TextView

    override fun createView(context: Context?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.insurance_item_view, null)
        titleTV = view.findViewById(R.id.title_tv)
        descTV = view.findViewById(R.id.desc_tv)
        thumbIV = view.findViewById(R.id.thumb_iv)
        companyTV = view.findViewById(R.id.company_tv)
        return view
    }
}