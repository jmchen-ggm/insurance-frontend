package com.bbinsurance.android.app.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.lib.log.Log
import com.bbinsurance.android.lib.util.PermissionUtil

/**
 * Created by jiaminchen on 2017/10/24.
 */
abstract class BaseActivity : Activity() {
    val TAG = "BB.BaseActivity"

    protected var titleTV : TextView ? = null
    protected var backIB : ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(getLayoutId())
        initView();
    }

    open fun initView() {
        titleTV = findViewById(R.id.title_tv);
        backIB = findViewById(R.id.back_ib);
        backIB!!.setOnClickListener({
            finish()
        })
    }

    fun setBBTitle(id : Int) {
        titleTV!!.setText(id)
    }

    fun setBBTitle(title : CharSequence) {
        titleTV!!.setText(title, TextView.BufferType.NORMAL)
    }

    abstract fun getLayoutId() : Int

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}