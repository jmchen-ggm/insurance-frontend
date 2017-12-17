package com.bbinsurance.android.app.plugin.account

import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
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
 * Created by jiaminchen on 17/12/16.
 */
class AccountLoginService : NetListener {
    private val TAG = "BB.AccountLoginService"

    private var accountListenerList = ArrayList<IAccountLoginListener>()

    fun addListener(listener: IAccountLoginListener) {
        accountListenerList.add(listener)
    }

    fun removeListener(listener: IAccountLoginListener) {
        accountListenerList.remove(listener)
    }

    override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
        for (listener : IAccountLoginListener in accountListenerList) {
            if (netResponse.respCode == 200) {
                listener.onLoginSuccess()
            } else {
                listener.onLoginFail()
            }
        }
    }

    override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
        if (netResponse.respCode == 200) {
            var respBody = netResponse.bbResp.Body.toString()
            BBLog.i(TAG, "onNetDoneInSubThread Log In Success %s", respBody)
            var bbLoginResponse = JSON.parseObject(respBody, BBLoginResponse::class.java)
            var contactEntity = bbUserToEntity(bbLoginResponse.UserInfo)
            contactStorage.insertContact(contactEntity)
            currentUser.Uin = contactEntity.Id
            currentUser.Username = contactEntity.Username
            currentUser.Token = ""
            currentUser.TokenUpdateTime = 0L
            var configEntity = ConfigEntity()
            configEntity.Key = AccountConstants.CurrentUserObjKey
            configEntity.Value = JSON.toJSONString(currentUser)
            BBCore.Instance.configCore.storage.insertConfig(configEntity)
        }
    }

    private fun bbUserToEntity(bbUser : BBUser) : ContactEntity {
        var contactEntity = ContactEntity()
        contactEntity.Id = bbUser.Id
        contactEntity.Username = bbUser.Username
        contactEntity.PhoneNumber = bbUser.PhoneNumber
        contactEntity.ThumbUrl = bbUser.ThumbUrl
        contactEntity.Timestamp = bbUser.Timestamp
        contactEntity.Nickname = bbUser.Nickname
        return contactEntity
    }

    override fun onNetTaskCancel(netRequest: NetRequest) {
        for (listener: IAccountLoginListener in accountListenerList) {
            listener.onLoginCancel()
        }
    }

    var currentUser : CurrentAccountConfigEntity
    var contactStorage : ContactStorage
    constructor() {
        var configStr = BBCore.Instance.configCore.storage.getConfigValue(AccountConstants.CurrentUserObjKey, "")
        if (!Util.isNullOrNil(configStr)) {
            currentUser = JSON.parseObject(configStr, CurrentAccountConfigEntity::class.java)
        } else {
            currentUser = CurrentAccountConfigEntity()
        }
        contactStorage = ContactStorage()
    }

    private var netRequest: NetRequest? = null

    fun logout() {
        currentUser = CurrentAccountConfigEntity()
        var configEntity = ConfigEntity()
        configEntity.Key = AccountConstants.CurrentUserObjKey
        configEntity.Value = JSON.toJSONString(currentUser)
        BBCore.Instance.configCore.storage.insertConfig(configEntity)
    }

    fun login(username: String, password: String) {
        var passwordMD5 = Util.MD5(password)
        BBLog.i(TAG, "login username:%s password:%s passwordMD5: %s", username, password, passwordMD5)
        var bbLoginRequest = BBLoginRequest()
        bbLoginRequest.Username = username
        bbLoginRequest.PasswordMD5 = passwordMD5
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncLogin, ProtocolConstants.URI.UserBin)
        netRequest.body = JSON.toJSONString(bbLoginRequest)
        this.netRequest = netRequest
        BBCore.Instance.netCore.startRequestAsync(netRequest, this)
    }

    fun getUIN(): Long {
        return currentUser.Uin
    }

    fun getUsername() : String {
        return currentUser.Username
    }

    fun isLogin() : Boolean {
        return !Util.isNullOrNil(currentUser.Username)
    }
}