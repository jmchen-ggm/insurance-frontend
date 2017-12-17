package com.bbinsurance.android.app.plugin.account.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.plugin.account.IAccountLoginListener
import com.bbinsurance.android.app.ui.BaseActivity
import com.bbinsurance.android.app.ui.widget.BBProgressDialog

/**
 * Created by jiaminchen on 17/12/9.
 */
class LoginUI : BaseActivity(), IAccountLoginListener {

    override fun onLoginSuccess() {
        dialog?.dismiss();
        finish()
    }

    override fun onLoginFail() {
        dialog?.dismiss()
    }

    override fun onLoginCancel() {
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
            login();
        })

        setBBTitle(R.string.login)

        BBCore.Instance.accountCore.loginService.addListener(this)

        setBackBtn(true, View.OnClickListener { finish() })
    }

    override fun onDestroy() {
        BBCore.Instance.accountCore.loginService.removeListener(this)
        super.onDestroy()
    }

    fun login() {
        var username = usernameET.text.toString()
        var password = passwordET.text.toString()
        BBCore.Instance.accountCore.loginService.login(username, password)
        dialog = BBProgressDialog(this)
        dialog?.show()
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