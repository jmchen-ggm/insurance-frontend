package com.bbinsurance.android.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.plugin.account.IAccountLoginListener
import com.bbinsurance.android.app.plugin.account.ui.LoginUI
import com.bbinsurance.android.app.ui.widget.BBProgressDialog
import com.bbinsurance.android.lib.log.BBLog
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 17/11/12.
 */
class MyFragmentUI : Fragment() {

    private val TAG = "BB.MyFragmentUI"

    private val LOGIN_REQUEST_CODE = 1;

    lateinit var avatarIV: SimpleDraweeView
    lateinit var nicknameTV: TextView
    lateinit var usernameTV: TextView
    lateinit var logoutBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.my_fragment_ui, container, false)
        avatarIV = view.findViewById(R.id.avatar_iv)
        nicknameTV = view.findViewById(R.id.nickname_tv)
        usernameTV = view.findViewById(R.id.username_tv)
        logoutBtn = view.findViewById(R.id.logout_btn)
        updateView()
        return view
    }

    var dialog: BBProgressDialog? = null
    private fun updateView() {
        if (BBCore.Instance.accountCore.loginService.isLogin()) {
            var currentUIN = BBCore.Instance.accountCore.loginService.getUIN()
            BBLog.i(TAG, "has login %d", currentUIN)
            var entity = BBCore.Instance.accountCore.syncService.getContact(currentUIN)
            nicknameTV.text = entity?.Nickname
            avatarIV.setImageURI(BBCore.Instance.accountCore.getContactThumbUrl(entity))
            usernameTV.text = entity?.Username
            logoutBtn.visibility = View.VISIBLE
        } else {
            nicknameTV.setText(R.string.login_request)
            avatarIV.setImageURI("res://sadf/" + R.drawable.tab_my_icon)
            usernameTV.visibility = View.GONE
            logoutBtn.visibility = View.GONE
        }
        avatarIV.setOnClickListener(loginClickListener)
        nicknameTV.setOnClickListener(loginClickListener)
        logoutBtn.setOnClickListener({
            dialog = BBProgressDialog(context)
            BBCore.Instance.threadCore.post(Runnable {
                BBCore.Instance.accountCore.loginService.logout()
                BBCore.Instance.uiHandler.post(Runnable {
                    updateView()
                })
            })
        })
    }

    private var loginClickListener = View.OnClickListener {
        if (!BBCore.Instance.accountCore.loginService.isLogin()) {
            var intent = Intent(context, LoginUI::class.java)
            startActivityForResult(intent, LOGIN_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LOGIN_REQUEST_CODE -> {
                updateView()
            }
        }
    }
}