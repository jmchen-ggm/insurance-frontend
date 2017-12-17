package com.bbinsurance.android.app.plugin.account

import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.ProtocolConstants.URL.Companion.FileServer
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.ConfigEntity
import com.bbinsurance.android.app.db.entity.ContactEntity
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.plugin.account.config.CurrentAccountConfigEntity
import com.bbinsurance.android.app.plugin.account.storage.ContactStorage
import com.bbinsurance.android.app.protocol.BBLoginRequest
import com.bbinsurance.android.app.protocol.BBLoginResponse
import com.bbinsurance.android.app.protocol.BBUser
import com.bbinsurance.android.lib.Util
import com.bbinsurance.android.lib.log.BBLog

/**
 * Created by jiaminchen on 17/11/17.
 */
class AccountCore {
    private val TAG = "BB.AccountCore"

    var loginService : AccountLoginService
    var syncService : AccountSyncService
    var contactStorage : ContactStorage

    constructor() {
        loginService = AccountLoginService()
        syncService = AccountSyncService()
        contactStorage = ContactStorage()
    }

    fun getContactThumbUrl(entity : ContactEntity?) : String{
        return FileServer + entity?.ThumbUrl
    }
}