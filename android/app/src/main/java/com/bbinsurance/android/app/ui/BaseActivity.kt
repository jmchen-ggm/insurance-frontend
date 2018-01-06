package com.bbinsurance.android.app.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.lib.log.BBLog
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


/**
 * Created by jiaminchen on 2017/10/24.
 */
abstract class BaseActivity : AppCompatActivity() {
    val TAG = "BB.BaseActivity"

    lateinit var actionBarView : View
    lateinit var actionBarParams : ActionBar.LayoutParams
    lateinit var titleTV : TextView
    lateinit var backBtn : ImageButton
    lateinit var optionBtn : ImageButton
    lateinit var optionTV : TextView

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
        actionBarParams = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)

        titleTV = actionBarView.findViewById(R.id.title_tv)
        backBtn = actionBarView.findViewById<ImageButton>(R.id.back_ib)
        optionBtn = actionBarView.findViewById(R.id.option_btn)
        optionTV = actionBarView.findViewById(R.id.option_tv)

        updateActionBar()
    }

    private fun updateActionBar() {
        supportActionBar?.setCustomView(actionBarView, actionBarParams)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
    }

    open fun needActionBar() : Boolean {
        return true;
    }

    open fun setBBTitle(id : Int) {
        titleTV.setText(id)
        updateActionBar()
    }

    open fun setBBTitle(title : String) {
        titleTV.text = title
        updateActionBar()
    }

    open fun setOptionBtn(srcId : Int, listener : View.OnClickListener) {
        optionBtn.visibility = View.VISIBLE
        optionBtn.setImageResource(srcId)
        optionBtn.setOnClickListener(listener)
        updateActionBar()
    }

    open fun setOptionTV(srcId : Int, listener : View.OnClickListener) {
        optionTV.visibility = View.VISIBLE
        optionTV.setText(srcId)
        optionTV.setOnClickListener(listener)
        updateActionBar()
    }

    open fun setBackBtn(visible: Boolean, listener: View.OnClickListener) {
        if (visible) {
            backBtn.visibility = View.VISIBLE
            backBtn.setOnClickListener(listener)
            updateActionBar()
        }
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

    fun hideVKB() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        imm.hideSoftInputFromWindow(actionBarView.windowToken, 0)
    }
}