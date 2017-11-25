package com.bbinsurance.android.app.db.storage

import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.lib.BBHandler

/**
 * Created by jiaminchen on 17/11/19.
 */
open class BBStorage {

    var listeners = ArrayList<BBStorageListener>()

    fun registerListener(listener : BBStorageListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    fun unRegisterListener(listener: BBStorageListener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener)
        }
    }

    fun notifySync(event : BBStorageEvent) {
        for (listener : BBStorageListener in listeners) {
            listener.onEvent(event)
        }
    }

    fun notifyMainThread(event: BBStorageEvent) {
        BBCore.Instance.uiHandler.post({
            for (listener: BBStorageListener in listeners) {
                listener.onEvent(event)
            }
        })
    }

    fun notifyAsync(event: BBStorageEvent) {
        BBCore.Instance.threadCore.post(Runnable {
            for (listener: BBStorageListener in listeners) {
                listener.onEvent(event)
            }
        })
    }
}