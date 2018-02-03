package com.bbinsurance.android.app.plugin.insurance.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bbinsurance.android.app.R

/**
 * Created by jiaminchen on 18/2/3.
 */
class InsuranceCompareChildDataItem {

    lateinit var child : InsuranceCompareChild

    open fun inflateView(context : Context, parent : ViewGroup?) : View {
        var view = LayoutInflater.from(context).inflate(R.layout.insurance_compare_child_item, parent, false)
        var viewHolder = Holder()
        viewHolder.nameTV = view.findViewById(R.id.name_tv)
        viewHolder.leftTV = view.findViewById(R.id.left_tv)
        viewHolder.rightTV = view.findViewById(R.id.right_tv)
        view.tag = (viewHolder)
        return view
    }

    open fun fillView(viewHolder: Holder) {
        viewHolder.nameTV.text = child.name
        viewHolder.leftTV.text = child.left
        viewHolder.rightTV.text = child.right
    }

    open class Holder {
        lateinit var nameTV : TextView
        lateinit var leftTV: TextView
        lateinit var rightTV: TextView
    }
}