package com.bbinsurance.android.app.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbinsurance.android.app.R

/**
 * Created by jiaminchen on 17/12/26.
 */
class AddFragmentUI : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return return inflater?.inflate(R.layout.add_fragment_ui, container, false)!!
    }
}