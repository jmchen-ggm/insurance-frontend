package com.bbinsurance.android.app.plugin.insurance.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bbinsurance.android.app.R

/**
 * Created by jiaminchen on 18/2/3.
 */
class InsuranceCompareGroupDataItem {

    lateinit var group : InsuranceCompareGroup
    var isExpand = false

    open fun inflateView(context : Context, parent : ViewGroup?) : View {
        var view = LayoutInflater.from(context).inflate(R.layout.insurance_compare_group_item, parent, false)
        var viewHolder = Holder()
        viewHolder.nameTV = view.findViewById(R.id.name_tv)
        viewHolder.forwordIV = view.findViewById(R.id.forward_iv)
        view.tag = (viewHolder)
        return view
    }

    open fun fillView(viewHolder: Holder) {
        viewHolder.nameTV.text = group.name
        if (isExpand) {
            viewHolder.forwordIV.setImageResource(R.drawable.down_forword_icon)
        } else {
            viewHolder.forwordIV.setImageResource(R.drawable.forward_icon)
        }
    }

    open class Holder {
        lateinit var nameTV : TextView
        lateinit var forwordIV : ImageView
    }
}