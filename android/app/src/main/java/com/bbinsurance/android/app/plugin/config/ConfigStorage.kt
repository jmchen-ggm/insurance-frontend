package com.bbinsurance.android.app.plugin.config

import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.dao.ConfigDao
import com.bbinsurance.android.app.db.entity.ConfigEntity
import com.bbinsurance.android.app.db.storage.BBStorage
import com.bbinsurance.android.app.db.storage.BBStorageEvent

/**
 * Created by jiaminchen on 17/11/22.
 */
class ConfigStorage : BBStorage(), ConfigDao {

    override fun getConfig(key: String): ConfigEntity {
        return BBCore.Instance.dbCore.db.configDao().getConfig(key)
    }

    fun getConfigValue(key : String, defaultValue : String) : String {
        var configEntity = BBCore.Instance.dbCore.db.configDao().getConfig(key)
        if (configEntity == null) {
            return defaultValue
        } else {
            return configEntity.Value
        }
    }

    override fun deleteConfig(key: String) {
        BBCore.Instance.dbCore.db.configDao().deleteConfig(key)
        notifyMainThread(BBStorageEvent(BBStorageEvent.DELETE, key))
    }

    override fun insertConfig(entity: ConfigEntity) {
        BBCore.Instance.dbCore.db.configDao().insertConfig(entity)
        notifyMainThread(BBStorageEvent(BBStorageEvent.INSERT, entity))
    }

    fun insertConfig(key : String, value : String) {
        var entity = ConfigEntity()
        entity.Key = key
        entity.Value = value
        insertConfig(entity)
    }
}