package com.bbinsurance.android.app.plugin.comment.ui

import android.database.Cursor
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.CommentEntity
import com.bbinsurance.android.app.db.storage.BBStorageEvent
import com.bbinsurance.android.app.db.storage.BBStorageListener
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.adapter.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem
import com.bbinsurance.android.app.ui.item.CommentDataItem
import com.bbinsurance.android.app.ui.item.EmptyDataItem
import com.bbinsurance.android.lib.log.BBLog

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentAdapter : BBBaseAdapter, BBStorageListener {

    override fun onEvent(event: BBStorageEvent) {
        BBCore.Instance.threadCore.post(Runnable {  resetCursor() })
    }

    val TAG = "BB.CommentAdapter"

    constructor(component: ListBaseUIComponent) : super(component) {
        BBCore.Instance.commentCore.commentStorage.registerListener(this)
        BBCore.Instance.commentCore.commentSyncService.startToSyncCommentList()
        BBCore.Instance.threadCore.post(Runnable {  resetCursor() })
    }

    private var dataCursor: Cursor? = null

    private fun resetCursor() {
        BBLog.i(TAG, "resetCursor")
        dataCursor = BBCore.Instance.commentCore.commentStorage.getAllComment()
        BBCore.Instance.uiHandler.post({
            clearCache()
            notifyDataSetChanged()
        })
    }

    override fun getCount(): Int {
        if (dataCursor != null) {
            return dataCursor!!.count
        } else {
            return 0
        }
    }

    override fun createDataItem(position: Int): BaseDataItem {
        if (dataCursor != null) {
            var commentDataItem = CommentDataItem(position)
            dataCursor!!.moveToPosition(position)
            commentDataItem.comment = CommentEntity()
            commentDataItem.comment.LocalId = dataCursor!!.getLong(0)
            commentDataItem.comment.ServerId = dataCursor!!.getLong(1)
            commentDataItem.comment.Uin = dataCursor!!.getLong(2)
            commentDataItem.comment.Content = dataCursor!!.getString(3)
            commentDataItem.comment.TotalScore = dataCursor!!.getInt(4)
            commentDataItem.comment.Score1 = dataCursor!!.getInt(5)
            commentDataItem.comment.Score2 = dataCursor!!.getInt(6)
            commentDataItem.comment.Score3 = dataCursor!!.getInt(7)
            commentDataItem.comment.Timestamp = dataCursor!!.getLong(8)
            commentDataItem.comment.ViewCount = dataCursor!!.getInt(9)
            commentDataItem.comment.Flags = dataCursor!!.getLong(10)
            return commentDataItem
        } else {
            return EmptyDataItem(position)
        }
    }

    override fun finish() {
        super.finish()
        BBCore.Instance.commentCore.commentStorage.unRegisterListener(this)
    }
}