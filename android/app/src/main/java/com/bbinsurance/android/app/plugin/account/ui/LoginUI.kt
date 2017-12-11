package com.bbinsurance.android.app.plugin.account.ui

import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.protocol.BBLoginRequest
import com.bbinsurance.android.app.protocol.BBLoginResponse
import com.bbinsurance.android.app.ui.BaseActivity
import com.bbinsurance.android.app.ui.widget.BBProgressDialog
import com.bbinsurance.android.lib.Util
import com.bbinsurance.android.lib.log.BBLog

/**
 * Created by jiaminchen on 17/12/9.
 */
class LoginUI : BaseActivity(), NetListener {
    override fun onNetTaskCancel(netRequest: NetRequest) {
        dialog?.dismiss()
    }

    override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
    }

    override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
        dialog?.dismiss()
        if (netResponse.respCode == 200) {
            var bbLoginResponse = JSON.parseObject(netResponse.bbResp.Body.toString(), BBLoginResponse::class.java)

        }
    }

    lateinit var usernameET : EditText
    lateinit var passwordET : EditText
    lateinit var loginBtn : Button

    var dialog : BBProgressDialog ? = null

    override fun initView() {
        super.initView()
        usernameET = findViewById(R.id.login_username_et)
        passwordET = findViewById(R.id.login_password_et)
        loginBtn = findViewById(R.id.login_btn)

        usernameET.addTextChangedListener(textWatcher)
        passwordET.addTextChangedListener(textWatcher)

        loginBtn.setOnClickListener({
            var username = usernameET.text.toString()
            var password = passwordET.text.toString()
            BBCore.Instance.accountCore.login(username, password)
        })

        setBBTitle(R.string.login)
    }

    var textWatcher = object : TextWatcher {
        override fun afterTextChanged(editable : Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
            loginBtn.isEnabled = usernameET.text.isNotEmpty() && passwordET.text.isNotEmpty()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.login_ui
    }
}