package com.bbinsurance.android.app.plugin.account

import com.bbinsurance.android.app.db.entity.ContactEntity

/**
 * Created by jiaminchen on 17/12/16.
 */
interface IAccountSyncListener {
    fun onAccountSyncSuccess(entity : ContactEntity)
    fun onAccountSyncFail()
}