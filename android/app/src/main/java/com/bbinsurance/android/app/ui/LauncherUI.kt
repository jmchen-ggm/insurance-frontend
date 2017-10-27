package com.bbinsurance.android.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.bbinsurance.android.app.AppConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.RecommendInsuranceAdapter
import com.bbinsurance.android.lib.util.PermissionUtil

/**
 * Created by jiaminchen on 2017/10/23.
 */


class LauncherUI : BaseListViewUI() {

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

    override fun getHeaderView(): View? {
        var headerView = LayoutInflater.from(getContext()).inflate(R.layout.launcher_header_view, null)

        compareLayout = headerView.findViewById(R.id.compare_layout)
        compareLayout!!.setOnClickListener({
            var intent = Intent(LauncherUI@this, InsuranceCompareUI::class.java)
            startActivity(intent)
        })
        consultLayout = headerView.findViewById(R.id.consult_layout)
        consultLayout!!.setOnClickListener({
            var intent = Intent(LauncherUI@this, InsuranceConsultUI::class.java)
            startActivity(intent)
        })
        evaluateLayout = headerView.findViewById(R.id.evaluate_layout)
        evaluateLayout!!.setOnClickListener({
            var intent = Intent(LauncherUI@this, InsuranceEvaluateUI::class.java)
            startActivity(intent)
        })
        learnLayout = headerView.findViewById(R.id.learn_layout)
        learnLayout!!.setOnClickListener({
            var intent = Intent(LauncherUI@this, InsuranceLearnUI::class.java)
            startActivity(intent)
        })
        return headerView
    }

    private var compareLayout : View ? = null
    private var consultLayout : View ? = null
    private var evaluateLayout : View ? = null
    private var learnLayout : View ? = null

    private var recommendInsuranceAdapter : RecommendInsuranceAdapter ? = null
    override fun getAdapter(): BBBaseAdapter {
        if (recommendInsuranceAdapter == null) {
            recommendInsuranceAdapter = RecommendInsuranceAdapter(this)
        }
        return recommendInsuranceAdapter!!
    }
}
