package com.bbinsurance.android.app.plugin.company.ui

import android.view.View
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.BaseListActivity
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter

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
}