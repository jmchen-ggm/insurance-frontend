package com.bbinsurance.android.app.plugin.account

import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.ContactEntity
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.protocol.BBGetUserRequest
import com.bbinsurance.android.app.protocol.BBGetUserResponse
import com.bbinsurance.android.app.protocol.BBUser

/**
 * Created by jiaminchen on 17/12/10.
 */
class AccountSyncService : NetListener {

    private var accountListenerList = ArrayList<IAccountSyncListener>()

    fun addListener(listener: IAccountSyncListener) {
        accountListenerList.add(listener)
    }

    fun removeListener(listener: IAccountSyncListener) {
        accountListenerList.remove(listener)
    }

    override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
        for (listener : IAccountSyncListener in accountListenerList) {
            if (netResponse.respCode == 200) {
                var bbGetUserResponse = JSON.parseObject(netResponse.bbResp.Body.toString(), BBGetUserResponse::class.java)
                var contactEntity = bbUserToEntity(bbGetUserResponse.User)
                listener.onAccountSyncSuccess(contactEntity)
            } else {
                listener.onAccountSyncFail()
            }
        }
    }

    override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
        if (netResponse.respCode == 200) {
            var bbGetUserResponse = JSON.parseObject(netResponse.bbResp.Body.toString(), BBGetUserResponse::class.java)
            var contactEntity = bbUserToEntity(bbGetUserResponse.User)
            BBCore.Instance.accountCore.contactStorage.insertContact(contactEntity)
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

    }

    fun getContact(id : Long) : ContactEntity? {
        var contactEntity = BBCore.Instance.accountCore.contactStorage.getContact(id)
        if (contactEntity == null) {
            getContactFromServer(id)
            return null
        } else {
            return contactEntity
        }
    }

    private fun getContactFromServer(id : Long) {
        var bbGetContactRequest = BBGetUserRequest()
        bbGetContactRequest.UserId = id
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncGetUser, ProtocolConstants.URI.UserBin)
        netRequest.body = JSON.toJSONString(bbGetContactRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, this)
    }
}