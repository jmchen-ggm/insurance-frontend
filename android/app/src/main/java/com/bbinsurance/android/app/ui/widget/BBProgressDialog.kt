package com.bbinsurance.android.app.ui.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.bbinsurance.android.app.R

/**
 * Created by jiaminchen on 17/12/9.
 */
class BBProgressDialog : Dialog {

    lateinit var tipTV : TextView

    constructor(context: Context) : super(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progress_dialog_ui)

        tipTV = findViewById(R.id.tip_tv)
        tipTV.setText(R.string.loading)
    }
}