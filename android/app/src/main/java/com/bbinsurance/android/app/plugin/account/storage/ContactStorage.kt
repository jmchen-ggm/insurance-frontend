package com.bbinsurance.android.app.plugin.account.storage

import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.dao.ContactDao
import com.bbinsurance.android.app.db.entity.ContactEntity
import com.bbinsurance.android.app.db.storage.BBStorage
import com.bbinsurance.android.app.db.storage.BBStorageEvent

/**
 * Created by jiaminchen on 17/12/10.
 */
class ContactStorage : BBStorage(), ContactDao {
    override fun getContact(id: Long): ContactEntity {
        return BBCore.Instance.dbCore.db.contactDao().getContact(id)
    }

    override fun insertContact(entity: ContactEntity) {
        BBCore.Instance.dbCore.db.contactDao().insertContact(entity)
        notifyMainThread(BBStorageEvent(BBStorageEvent.INSERT, entity))
    }

    override fun deleteContact(username: String) {
        BBCore.Instance.dbCore.db.contactDao().deleteContact(username)
        notifyMainThread(BBStorageEvent(BBStorageEvent.DELETE, username))
    }

    override fun getContact(username: String): ContactEntity {
        return BBCore.Instance.dbCore.db.contactDao().getContact(username)
    }
}