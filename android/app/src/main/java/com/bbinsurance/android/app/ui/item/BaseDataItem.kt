package com.bbinsurance.android.app.ui.item

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * Created by jiaminchen on 17/10/25.
 */
open class BaseDataItem {

    var position : Int
    var viewType : Int

    constructor(viewType : Int, position : Int) {
        this.viewType = viewType
        this.position = position
    }

    var isFillData : Boolean = false

    open fun inflateView(context : Context, parent : ViewGroup?, itemView : View?) : View {
        TODO()
    }

    open fun fillData(context: Context, viewHolder: BaseViewHolder) {

    }

    open fun fillView(context: Context, viewHolder: BaseViewHolder) {

    }

    open class BaseViewHolder {

    }
}