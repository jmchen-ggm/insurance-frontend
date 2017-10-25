package com.bbinsurance.android.app.ui.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants

/**
 * Created by jiaminchen on 2017/10/25.
 */
class EmptyDataItem : BaseDataItem {

    constructor(position : Int) : super(UIConstants.ListViewType.UnKnown, position) {

    }

    override fun inflateView(context: Context, parent: ViewGroup?, itemView: View?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.empty_item_view, parent, false)
        view.tag = BaseViewHolder()
        return view
    }
}