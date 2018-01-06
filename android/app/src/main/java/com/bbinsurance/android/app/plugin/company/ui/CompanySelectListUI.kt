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
class CompanySelectListUI : BaseListActivity() {

    var adapter: CompanyAdapter? = null

    override fun getAdapter(): BBBaseAdapter {
        if (adapter == null) {
            adapter = CompanyAdapter(this)
        }
        return adapter!!
    }

    override fun getLayoutId(): Int {
        return R.layout.company_ui
    }

    override fun initView() {
        super.initView()
        setBBTitle(R.string.company_list_title)
        setBackBtn(true, View.OnClickListener {
            finish()
        })
        adapter?.refreshCompanyList()
    }

    override fun handleItemClick(view: View, dataItem: BaseDataItem, isHandle: Boolean): Boolean {
        var companyDataItem = dataItem as CompanyDataItem
        var returnStr = JSON.toJSONString(companyDataItem.company)
        var returnIntent = Intent()
        returnIntent.putExtra(UIConstants.CompanySelectListUI.KeySelectCompany, returnStr)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
        return true
    }
}