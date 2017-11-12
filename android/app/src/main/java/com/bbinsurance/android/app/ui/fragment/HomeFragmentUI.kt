package com.bbinsurance.android.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.InsuranceCompareUI
import com.bbinsurance.android.app.ui.adapter.ListBaseUIComponent
import com.bbinsurance.android.app.ui.adapter.RecommendInsuranceAdapter
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 17/11/12.
 */
class HomeFragmentUI : Fragment(), ListBaseUIComponent {
    private lateinit var lv : ListView
    private lateinit var compareLayout : View
    private lateinit var consultLayout : View
    private lateinit var evaluateLayout : View
    private lateinit var learnLayout : View
    private lateinit var recommendInsuranceAdapter : RecommendInsuranceAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var convertView = inflater?.inflate(R.layout.home_fragment_ui, container, false)
        if (convertView != null) {
            lv = convertView.findViewById<ListView>(R.id.listview)
            lv.addHeaderView(getHeaderView())
            recommendInsuranceAdapter = RecommendInsuranceAdapter(this)
            lv.adapter = recommendInsuranceAdapter
        }
        return convertView
    }

    fun getHeaderView(): View {
        var headerView = LayoutInflater.from(getContext()).inflate(R.layout.launcher_header_view, null)

        compareLayout = headerView.findViewById(R.id.compare_layout)
        compareLayout!!.setOnClickListener({
            var intent = Intent(context, InsuranceCompareUI::class.java)
            startActivity(intent)
        })
        consultLayout = headerView.findViewById(R.id.consult_layout)
        consultLayout!!.setOnClickListener({
            var intent = Intent(context, InsuranceCompareUI::class.java)
            startActivity(intent)
        })
        evaluateLayout = headerView.findViewById(R.id.evaluate_layout)
        evaluateLayout!!.setOnClickListener({
            var intent = Intent(context, InsuranceCompareUI::class.java)
            startActivity(intent)
        })
        learnLayout = headerView.findViewById(R.id.learn_layout)
        learnLayout!!.setOnClickListener({
            var intent = Intent(context, InsuranceCompareUI::class.java)
            startActivity(intent)
        })
        return headerView
    }

    override fun onItemClick(view: View, dataItem: BaseDataItem, isHandled: Boolean) {

    }

    override fun getListView(): ListView {
        return lv
    }
}