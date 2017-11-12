package com.bbinsurance.android.app.ui

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.lib.log.BBLog


/**
 * Created by jiaminchen on 2017/10/24.
 */
abstract class BaseActivity : AppCompatActivity() {
    val TAG = "BB.BaseActivity"

    lateinit var actionBarView : View
    lateinit var actionBarParams : ActionBar.LayoutParams
    lateinit var titleTV : TextView
    lateinit var backBtn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BBLog.d(TAG, "onCreate")
        setContentView(getLayoutId())
        initData()
        initView()
    }

    open fun initData() {

    }

    open fun initView() {
        if (needActionBar()) {
            initActionBar()
        } else {
            supportActionBar?.hide()
        }
    }

    private fun initActionBar() {
        actionBarView = LayoutInflater.from(this).inflate(R.layout.bb_action_bar, null)
        actionBarParams = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)

        titleTV = actionBarView.findViewById(R.id.title_tv)

        if (getTitleId() != 0) {
            titleTV.setText(getTitleId())
        }

        backBtn = actionBarView.findViewById<ImageButton>(R.id.back_ib)
        if (getBackBtnVisible()) {
            backBtn.visibility = View.VISIBLE
            backBtn.setOnClickListener(getBackBtnListener())
        } else {
            backBtn.visibility = View.GONE
        }

        supportActionBar?.setCustomView(actionBarView, actionBarParams)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
    }

    open fun needActionBar() : Boolean {
        return true;
    }

    open fun getTitleId() : Int {
        return 0;
    }

    open fun getBackBtnVisible() : Boolean {
        return true
    }

    open fun getBackBtnListener() : View.OnClickListener ? {
        return null
    }

    abstract fun getLayoutId() : Int

    override fun onDestroy() {
        super.onDestroy()
        BBLog.d(TAG, "onDestroy")
    }

    override fun onResume() {
        super.onResume()
        BBLog.d(TAG, "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        BBLog.d(TAG, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        BBLog.d(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        BBLog.d(TAG, "onStop")
    }

    override fun onPause() {
        super.onPause()
        BBLog.d(TAG, "onPause")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}