package com.bbinsurance.android.app.plugin.account

import com.bbinsurance.android.app.db.entity.ContactEntity

/**
 * Created by jiaminchen on 17/12/10.
 */
interface IAccountLoginListener {
    fun onLoginSuccess()
    fun onLoginFail()
    fun onLoginCancel()
}