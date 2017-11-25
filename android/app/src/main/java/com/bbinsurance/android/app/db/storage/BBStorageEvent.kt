package com.bbinsurance.android.app.db.storage

/**
 * Created by jiaminchen on 17/11/19.
 */
class BBStorageEvent {
    var action : Int = 0
    var resultCode : Int = 0
    var obj : Any ? = null

    companion object {
        val INSERT = 1
        val UPDATE = 2
        val DELETE = 3
    }

    constructor() {

    }

    constructor(action : Int) {
        this.action = action
    }

    constructor(action: Int, obj : Any) {
        this.action = action
        this.obj = obj
    }
}