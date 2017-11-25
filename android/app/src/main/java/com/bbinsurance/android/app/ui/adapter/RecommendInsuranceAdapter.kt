package com.bbinsurance.android.app.ui.adapter

import com.bbinsurance.android.app.entity.InsuranceEntity
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.item.RecommendInsuranceDataItem

/**
 * Created by jiaminchen on 2017/10/25.
 */
class RecommendInsuranceAdapter : BBBaseAdapter{
    private val TAG = "BB.RecommendInsuranceAdapter"
    private var insuranceList : List<InsuranceEntity> = ArrayList<InsuranceEntity>()

    constructor(uiComponent: ListBaseUIComponent) : super(uiComponent) {
        refreshRecommendInsuranceList()
    }

    override fun getCount(): Int {
        return insuranceList.size
    }

    override fun createDataItem(position: Int): BaseDataItem {
        var index = position
        var entity = insuranceList.get(index)
        var dataItem = RecommendInsuranceDataItem(position)
        dataItem.insuranceEntity = entity
        return dataItem
    }

    private fun refreshRecommendInsuranceList() {

    }
}