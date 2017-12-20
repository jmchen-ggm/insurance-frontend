package com.bbinsurance.android.app.ui.fragment

import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.DiscoverAdapter

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
}