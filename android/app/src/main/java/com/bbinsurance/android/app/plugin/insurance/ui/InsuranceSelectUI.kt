package com.bbinsurance.android.app.plugin.insurance.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Spinner
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.ui.BaseListActivity
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.InsuranceAdapter
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 18/2/3.
 */
class InsuranceSelectUI : BaseListActivity() {

    lateinit var selectCompanySpinner : Spinner
    lateinit var selectInsuranceTypeSpinner : Spinner

    override fun initView() {
        super.initView()

        setBBTitle(R.string.init_compare_insurance)
        setBackBtn(true, View.OnClickListener {
            finish()
        })

        selectCompanySpinner = findViewById(R.id.select_company_spinner)
        selectInsuranceTypeSpinner = findViewById(R.id.select_insurance_type_spinner)

    }

    override fun getLayoutId(): Int {
        return R.layout.insurance_select_ui
    }

    private var adapter: InsuranceAdapter? = null

    override fun getAdapter(): BBBaseAdapter {
        if (adapter == null) {
            adapter = InsuranceAdapter(this)
        }
        return adapter!!
    }

    override fun handleItemClick(view: View, dataItem: BaseDataItem, isHandle: Boolean): Boolean {
        var insuranceDataItem = dataItem as InsuranceDataItem
        if (insuranceDataItem.entity.InsuranceTypeId == 1L) {
            var intent = Intent()
            intent.putExtra(UIConstants.InsuranceSelectUI.KeyInsuranceId, insuranceDataItem.entity.Id)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        return true
    }
}