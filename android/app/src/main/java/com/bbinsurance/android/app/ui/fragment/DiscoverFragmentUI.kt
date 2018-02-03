package com.bbinsurance.android.app.ui.fragment

import android.content.Intent
import android.view.View
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.plugin.insurance.ui.InsuranceDataItem
import com.bbinsurance.android.app.plugin.insurance.ui.InsuranceDetailUI
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.DiscoverAdapter
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 17/11/12.
 */
class DiscoverFragmentUI : BaseListFragment() {

    private var discoverAdapter: DiscoverAdapter? = null

    override fun getAdapter(): BBBaseAdapter {
        if (discoverAdapter == null) {
            discoverAdapter = DiscoverAdapter(this)
        }
        return discoverAdapter!!
    }

    override fun getLayoutId(): Int {
        return R.layout.discover_fragment_ui
    }

    override fun handleItemClick(view: View, dataItem: BaseDataItem, isHandle: Boolean): Boolean {
        var insuranceDataItem = dataItem as InsuranceDataItem
        if (insuranceDataItem.entity.InsuranceTypeId == 1L) {
            var intent = Intent(context, InsuranceDetailUI::class.java)
            intent.putExtra(UIConstants.InsuranceDetailUI.KeyInsuranceId, insuranceDataItem.entity.Id)
            startActivity(intent)
        }
        return true
    }
}