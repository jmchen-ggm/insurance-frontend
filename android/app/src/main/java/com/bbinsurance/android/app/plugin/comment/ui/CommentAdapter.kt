package com.bbinsurance.android.app.plugin.comment.ui

import android.content.Intent
import android.view.View
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.ContactEntity
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.plugin.account.AccountCore
import com.bbinsurance.android.app.plugin.account.IAccountSyncListener
import com.bbinsurance.android.app.plugin.account.ui.LoginUI
import com.bbinsurance.android.app.protocol.*
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.component.ListBaseUIComponent
import com.bbinsurance.android.app.ui.item.BaseDataItem

/**
 * Created by jiaminchen on 17/11/17.
 */
class CommentAdapter : BBBaseAdapter, IAccountSyncListener {

    override fun onAccountSyncSuccess(entity: ContactEntity) {
        notifyDataSetChanged()
    }

    override fun onAccountSyncFail() {
        notifyDataSetChanged()
    }

    private var commentList = ArrayList<BBComment>()
    fun refreshCommentList(isFromStart: Boolean) {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncListComment, ProtocolConstants.URI.DataBin)
        var listCommentRequest = BBListCommentRequest()
        if (isFromStart) {
            listCommentRequest.StartIndex = 0
        } else {
            listCommentRequest.StartIndex = commentList.size
        }
        listCommentRequest.PageSize = 20
        netRequest.body = JSON.toJSONString(listCommentRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, object : NetListener {
            override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
                if (netResponse.respCode == 200) {
                    var listCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                            BBListCommentResponse::class.java)
                    if (isFromStart) {
                        commentList.clear()
                    }
                    commentList.addAll(listCommentResponse.CommentList)
                    uiComponent.onLoadMoreFinish()
                    if (isFromStart) {
                        clearCache()
                    }
                    notifyDataSetChanged()
                }
            }

            override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
            }

            override fun onNetTaskCancel(netRequest: NetRequest) {
            }
        })
    }

    val TAG = "BB.CommentAdapter"

    constructor(component: ListBaseUIComponent) : super(component) {
        BBCore.Instance.accountCore.syncService.addListener(this)
    }

    override fun getCount(): Int {
        return commentList.size
    }

    override fun createDataItem(position: Int): BaseDataItem {
        var commentDataItem = CommentDataItem(position)
        commentDataItem.comment = commentList[position]
        commentDataItem.upNetListener = upNetListener
        commentDataItem.commentClickListener = commentClickListener
        return commentDataItem
    }

    override fun finish() {
        super.finish()
        BBCore.Instance.accountCore.syncService.removeListener(this)
    }

    private var upNetListener = object : NetListener {
        override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
            if (netResponse.respCode == 200) {
                var upCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                        BBUpCommentResponse::class.java)
                for (i in commentList.indices) {
                    if (commentList[i].Id == upCommentResponse.Comment.Id) {
                        commentList[i] = upCommentResponse.Comment
                        break
                    }
                }
                clearCache()
                notifyDataSetChanged()
            }
        }

        override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
        }

        override fun onNetTaskCancel(netRequest: NetRequest) {
        }
    }

    override fun handleItemClick(view: View, dataItem: BaseDataItem, isHandle: Boolean) {
        var commentDataItem = dataItem as CommentDataItem
        viewComment(commentDataItem.comment.Id)
    }

    private fun viewComment(commentId: Long) {
        var netRequest = NetRequest(ProtocolConstants.FunId.FuncViewComment, ProtocolConstants.URI.DataBin)
        var viewCommentRequest = BBViewCommentRequest()
        viewCommentRequest.Id = commentId
        netRequest.body = JSON.toJSONString(viewCommentRequest)
        BBCore.Instance.netCore.startRequestAsync(netRequest, viewNetListener)
    }

    private var viewNetListener = object : NetListener {
        override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
            if (netResponse.respCode == 200) {
                var viewCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                        BBViewCommentResponse::class.java)
                for (i in commentList.indices) {
                    if (commentList[i].Id == viewCommentResponse.Comment.Id) {
                        commentList[i] = viewCommentResponse.Comment
                        break
                    }
                }
                clearCache()
                notifyDataSetChanged()
            }
        }

        override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
        }

        override fun onNetTaskCancel(netRequest: NetRequest) {
        }
    }

    private var commentClickListener = View.OnClickListener { view ->
        if (BBCore.Instance.accountCore.loginService.isLogin()) {
            var comment = view?.getTag() as BBComment
            var intent = Intent(uiComponent.getComponentContext(), CommentDetailUI::class.java)
            intent.putExtra(UIConstants.CommentDetailUI.KeyComment, JSON.toJSONString(comment))
            intent.putExtra(UIConstants.CommentDetailUI.KeyNeedKeyboard, true)
            uiComponent.getComponentContext().startActivity(intent)
        } else {
            AccountCore.goToLoginUI(uiComponent.getComponentContext(), Intent())
        }
    }
}