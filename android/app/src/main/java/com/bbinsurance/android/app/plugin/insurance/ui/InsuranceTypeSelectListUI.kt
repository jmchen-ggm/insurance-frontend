package com.bbinsurance.android.app.plugin.company.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.ui.BaseListActivity
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 18/1/6.
 */
class InsuranceTypeSelectListUI : BaseListActivity() {

    var adapter: InsuranceTypeAdapter? = null

    override fun getAdapter(): BBBaseAdapter {
        if (adapter == null) {
            adapter = InsuranceTypeAdapter(this)
        }
        return adapter!!
    }

    override fun getLayoutId(): Int {
        return R.layout.company_ui
    }

    override fun initView() {
        super.initView()
        setBBTitle(R.string.insurance_type_list_title)
        setBackBtn(true, View.OnClickListener {
            finish()
        })
        adapter?.refreshCompanyList()
    }

    override fun handleItemClick(view: View, dataItem: BaseDataItem, isHandle: Boolean): Boolean {
        var insuranceTypeDataItem = dataItem as InsuranceTypeDataItem
        var returnStr = JSON.toJSONString(insuranceTypeDataItem.insuranceType)
        var returnIntent = Intent()
        returnIntent.putExtra(UIConstants.InsuranceSelectListUI.KeySelectInsuranceType, returnStr)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
        return true
    }
}