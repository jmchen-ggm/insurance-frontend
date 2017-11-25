package com.bbinsurance.android.app.plugin.comment

import com.bbinsurance.android.app.plugin.comment.storage.CommentStorage

/**
 * Created by jiaminchen on 17/11/19.
 */
class CommentCore {

    var commentSyncService : CommentSyncService
    var commentStorage : CommentStorage

    constructor() {
        commentSyncService = CommentSyncService()
        commentStorage = CommentStorage()
    }
}