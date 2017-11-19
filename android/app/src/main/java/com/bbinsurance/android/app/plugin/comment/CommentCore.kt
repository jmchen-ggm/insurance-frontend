package com.bbinsurance.android.app.plugin.comment

/**
 * Created by jiaminchen on 17/11/19.
 */
class CommentCore {

    var commentSyncService : CommentSyncService

    constructor() {
        commentSyncService = CommentSyncService()
    }
}