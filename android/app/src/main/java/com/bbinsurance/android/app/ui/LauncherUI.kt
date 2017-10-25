package com.bbinsurance.android.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bbinsurance.android.app.AppConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.lib.util.PermissionUtil

/**
 * Created by jiaminchen on 2017/10/23.
 */


class LauncherUI : BaseActivity(), ListBaseUIComponent {

    override fun getLayoutId(): Int {
        return R.layout.launcher_ui
    }

    private val REQUEST_PERMISSION_CODE = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (permission in AppConstants.REQUST_PERMISSION) {
            PermissionUtil.verifyPermissions(this, permission, REQUEST_PERMISSION_CODE)
        }

        setBBTitle(R.string.app_name)
        backIB!!.visibility = View.GONE
    }

    override fun initView() {
        super.initView()
        compareLayout = findViewById(R.id.compare_layout)
        compareLayout!!.setOnClickListener({
            var intent = Intent(LauncherUI@this, InsuranceCompareUI::class.java)
            startActivity(intent)
        })
        consultLayout = findViewById(R.id.consult_layout)
        consultLayout!!.setOnClickListener({
            var intent = Intent(LauncherUI@this, InsuranceConsultUI::class.java)
            startActivity(intent)
        })
        evaluateLayout = findViewById(R.id.evaluate_layout)
        evaluateLayout!!.setOnClickListener({
            var intent = Intent(LauncherUI@this, InsuranceEvaluateUI::class.java)
            startActivity(intent)
        })
        learnLayout = findViewById(R.id.learn_layout)
        learnLayout!!.setOnClickListener({
            var intent = Intent(LauncherUI@this, InsuranceLearnUI::class.java)
            startActivity(intent)
        })
    }

    private var compareLayout : View ? = null
    private var consultLayout : View ? = null
    private var evaluateLayout : View ? = null
    private var learnLayout : View ? = null

    override fun onItemClick(view: View, dataItem: BaseDataItem, isHandled: Boolean) {

    }

    override fun getContext(): Context {
        return LauncherUI@this
    }

}
