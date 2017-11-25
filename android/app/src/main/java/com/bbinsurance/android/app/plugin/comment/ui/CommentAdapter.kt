package com.bbinsurance.android.app.plugin.comment.ui

import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.CommentEntity
import com.bbinsurance.android.app.db.storage.BBStorageEvent
import com.bbinsurance.android.app.db.storage.BBStorageListener
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.item.CommentDataItem
import com.bbinsurance.android.lib.log.BBLog

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentAdapter : BBBaseAdapter, BBStorageListener {

    override fun onEvent(event: BBStorageEvent) {
        refreshCommentList()
    }

    val TAG = "BB.CommentAdapter"

    constructor(component: ListBaseUIComponent) : super(component) {
        refreshCommentList()
        BBCore.Instance.commentCore.commentStorage.registerListener(this)
        BBCore.Instance.commentCore.commentSyncService.startToSyncCommentList()
    }

    private var commentList = ArrayList<CommentEntity>()

    private fun refreshCommentList() {
        BBCore.Instance.threadCore.post(Runnable {
            var limit = 20
            var offset = 0
            if (commentList.size > 0) {
                offset = commentList.size
            }
            var limitList = BBCore.Instance.commentCore.commentStorage.getCommentListLimit(limit, offset)
            commentList.addAll(limitList)
            BBLog.i(TAG, "commentList Size=%d", commentList.size)
            BBCore.Instance.uiHandler.post({
                notifyDataSetChanged()
            })
        })
    }

    override fun getCount(): Int {
        return commentList.size
    }

    override fun createDataItem(position: Int): BaseDataItem {
        var commentDataItem = CommentDataItem(position)
        commentDataItem.comment = commentList.get(position)
        return commentDataItem
    }

    override fun finish() {
        super.finish()
        BBCore.Instance.commentCore.commentStorage.unRegisterListener(this)
    }
}