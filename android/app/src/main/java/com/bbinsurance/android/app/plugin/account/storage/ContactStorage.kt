package com.bbinsurance.android.app.plugin.account.storage

import android.util.LruCache
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.dao.ContactDao
import com.bbinsurance.android.app.db.entity.ContactEntity
import com.bbinsurance.android.app.db.storage.BBStorage
import com.bbinsurance.android.app.db.storage.BBStorageEvent

/**
 * Created by jiaminchen on 17/12/10.
 */
class ContactStorage : BBStorage(), ContactDao {

    var contactCache = LruCache<Long, ContactEntity>(100)

    override fun getContact(id: Long): ContactEntity {
        if (contactCache.get(id) != null) {
            return contactCache.get(id)
        } else {
            var entity = BBCore.Instance.dbCore.db.contactDao().getContact(id)
            if (entity != null) {
                contactCache.put(id, entity)
            }
            return entity
        }
    }

    override fun insertContact(entity: ContactEntity) {
        contactCache.put(entity.Id, entity)
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