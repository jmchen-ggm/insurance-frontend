package com.bbinsurance.android.app.ui.item

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * Created by jiaminchen on 17/10/25.
 */
open class BaseDataItem {

    var isFillData : Boolean = false

    fun inflateView(context : Context, parent : ViewGroup?, convertView : View?) : View {
        TODO()
    }

    fun fillData(context: Context, viewHolder: BaseViewHolder) {

    }

    fun fillView(context: Context, viewHolder: BaseViewHolder) {

    }


    open class BaseViewHolder {

    }
}