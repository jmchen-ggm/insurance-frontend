package com.bbinsurance.android.app.plugin.comment.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.bbinsurance.android.app.ProtocolConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.ContactEntity
import com.bbinsurance.android.app.net.NetListener
import com.bbinsurance.android.app.net.NetRequest
import com.bbinsurance.android.app.net.NetResponse
import com.bbinsurance.android.app.protocol.BBComment
import com.bbinsurance.android.app.protocol.BBUpCommentRequest
import com.bbinsurance.android.app.protocol.BBUpCommentResponse
import com.bbinsurance.android.app.ui.BaseListActivity
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.widget.FrescoUtil
import com.bbinsurance.android.lib.util.TimeUtil
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 18/1/13.
 */
class CommentDetailUI : BaseListActivity() {

    var adapter : CommentDetailAdapter ? = null
    override fun getAdapter(): BBBaseAdapter {
        if (adapter == null) {
            adapter = CommentDetailAdapter(this)
        }
        return adapter!!
    }

    override fun initData() {
        var commentStr = intent.getStringExtra(UIConstants.CommentDetailUI.KeyComment)
        comment = JSON.parseObject(commentStr, BBComment::class.java)
    }

    override fun initView() {
        super.initView()
        setBackBtn(true, View.OnClickListener {
            finish()
        })
        setBBTitle(R.string.comment_detail_title)
    }

    lateinit var avatarIV: SimpleDraweeView
    lateinit var nickNameTV: TextView
    lateinit var contentTV: TextView
    lateinit var viewTV: TextView
    lateinit var upTV: TextView
    lateinit var replyTV: TextView
    lateinit var likeIV: ImageView
    lateinit var likeTV: TextView
    lateinit var commentLayout: View
    lateinit var likeLayout: View
    lateinit var timeTV: TextView
    lateinit var scoreTV: TextView
    var starsIV = arrayOfNulls<ImageView?>(5)
    override fun getHeaderView(): View? {
        var header = layoutInflater.inflate(R.layout.comment_detail_header, null)
        avatarIV = header.findViewById(R.id.avatar_iv)
        nickNameTV = header.findViewById(R.id.nickname_tv)
        timeTV = header.findViewById(R.id.time_tv)
        scoreTV = header.findViewById(R.id.score_tv)
        viewTV = header.findViewById(R.id.view_tv)
        upTV = header.findViewById(R.id.up_tv)
        replyTV = header.findViewById(R.id.reply_tv)
        likeIV = header.findViewById(R.id.like_iv)
        likeTV = header.findViewById(R.id.like_tv)
        commentLayout = header.findViewById(R.id.comment_layout)
        likeLayout = header.findViewById(R.id.like_layout)
        contentTV = header.findViewById(R.id.content_tv)
        starsIV[0] = header.findViewById(R.id.star_1_iv)
        starsIV[1] = header.findViewById(R.id.star_2_iv)
        starsIV[2] = header.findViewById(R.id.star_3_iv)
        starsIV[3] = header.findViewById(R.id.star_4_iv)
        starsIV[4] = header.findViewById(R.id.star_5_iv)
        updateHeaderView()
        return header
    }
    
    lateinit var comment : BBComment
    var contactEntity : ContactEntity ? = null
    fun updateHeaderView() {
        if (contactEntity == null) {
            contactEntity = BBCore.Instance.accountCore.syncService.getContact(comment.Uin)
        }
        avatarIV.hierarchy = FrescoUtil.getCircleHierarchy()
        if (contactEntity == null) {
            nickNameTV.text = String.format("%d", comment.Uin)
            avatarIV.setImageURI("res://sadf/" + R.drawable.tab_my_icon)
        } else {
            nickNameTV.text = contactEntity?.Nickname
            avatarIV.setImageURI(BBCore.Instance.accountCore.getContactThumbUrl(contactEntity))
        }
        var scoreIndex = comment.TotalScore / 20
        for (index: Int in starsIV.indices) {
            if (index < scoreIndex) {
                starsIV[index]?.setImageResource(R.drawable.star_yellow)
            } else {
                starsIV[index]?.setImageResource(R.drawable.star_gray)
            }
        }
        scoreTV.text = getString(R.string.comment_star_info, scoreIndex)
        timeTV.text = TimeUtil.formatTimeInList(this, comment.Timestamp)
        contentTV.text = comment.Content.trim()
        viewTV.text = getString(R.string.comment_view_info, comment.ViewCount)
        upTV.text = getString(R.string.comment_up_info, comment.UpCount)
        replyTV.text = getString(R.string.comment_reply_info, comment.ReplyCount)
        likeLayout.setOnClickListener({
            var netRequest = NetRequest(ProtocolConstants.FunId.FuncUpComment, ProtocolConstants.URI.DataBin)
            var upCommentRequest = BBUpCommentRequest()
            upCommentRequest.CommentUp.Uin = BBCore.Instance.accountCore.loginService.getUIN()
            upCommentRequest.CommentUp.CommentId = comment.Id
            upCommentRequest.IsUp = !comment.IsUp
            netRequest.body = JSON.toJSONString(upCommentRequest)
            BBCore.Instance.netCore.startRequestAsync(netRequest, upNetListener)
        })
        if (comment.IsUp) {
            likeIV.setImageResource(R.drawable.like_icon_yellow)
            likeLayout.setBackgroundResource(R.drawable.comment_like_yellow_bg)
        } else {
            likeIV.setImageResource(R.drawable.like_icon)
            likeLayout.setBackgroundResource(R.drawable.comment_like_bg)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.comment_detail_ui
    }

    private var upNetListener = object : NetListener {
        override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
            if (netResponse.respCode == 200) {
                var upCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                        BBUpCommentResponse::class.java)
                comment = upCommentResponse.Comment
                updateHeaderView()
            }
        }

        override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
        }

        override fun onNetTaskCancel(netRequest: NetRequest) {
        }
    }
}