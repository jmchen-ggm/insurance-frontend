package com.bbinsurance.android.app.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.LauncherUI
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.ListBaseUIComponent
import com.bbinsurance.android.app.ui.adapter.RecommendInsuranceAdapter
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 17/11/12.
 */
class HomeFragmentUI : BaseListFragment(), ListBaseUIComponent {
    override fun getLayoutId(): Int {
        return R.layout.home_fragment_ui
    }

    private var recommendInsuranceAdapter : RecommendInsuranceAdapter ? = null
    override fun getAdapter(): BBBaseAdapter {
        if (recommendInsuranceAdapter == null) {
            recommendInsuranceAdapter = RecommendInsuranceAdapter(this)
        }
        return recommendInsuranceAdapter!!
    }

    private lateinit var compareLayout : View
    private lateinit var consultLayout : View
    private lateinit var evaluateLayout : View
    private lateinit var learnLayout : View

    override fun getHeaderView(): View {
        var headerView = LayoutInflater.from(getContext()).inflate(R.layout.launcher_header_view, null)

        compareLayout = headerView.findViewById(R.id.compare_layout)
        compareLayout?.setOnClickListener({
            var intent = Intent(context, LauncherUI::class.java)
            startActivity(intent)
        })
        consultLayout = headerView.findViewById(R.id.consult_layout)
        consultLayout!!.setOnClickListener({
            var intent = Intent(context, LauncherUI::class.java)
            startActivity(intent)
        })
        evaluateLayout = headerView.findViewById(R.id.evaluate_layout)
        evaluateLayout!!.setOnClickListener({
            var intent = Intent(context, LauncherUI::class.java)
            startActivity(intent)
        })
        learnLayout = headerView.findViewById(R.id.learn_layout)
        learnLayout!!.setOnClickListener({
            var intent = Intent(context, LauncherUI::class.java)
            startActivity(intent)
        })
        return headerView
    }

    override fun onItemClick(view: View, dataItem: BaseDataItem, isHandled: Boolean) {

    }
}