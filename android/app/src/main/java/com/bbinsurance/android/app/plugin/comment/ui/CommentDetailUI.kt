package com.bbinsurance.android.app.plugin.comment.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.bbinsurance.android.app.protocol.*
import com.bbinsurance.android.app.ui.BaseListActivity
import com.bbinsurance.android.app.ui.adapter.BBBaseAdapter
import com.bbinsurance.android.app.ui.widget.FrescoUtil
import com.bbinsurance.android.lib.util.TimeUtil
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by jiaminchen on 18/1/13.
 */
class CommentDetailUI : BaseListActivity() {

    var adapter: CommentDetailAdapter? = null
    override fun getAdapter(): BBBaseAdapter {
        if (adapter == null) {
            adapter = CommentDetailAdapter(this)
        }
        return adapter!!
    }

    var replyUin : Long = 0L
    override fun initData() {
        var commentStr = intent.getStringExtra(UIConstants.CommentDetailUI.KeyComment)
        comment = JSON.parseObject(commentStr, BBComment::class.java)
        needKeyboard = intent.getBooleanExtra(UIConstants.CommentDetailUI.KeyNeedKeyboard, false)
    }

    override fun initView() {
        super.initView()
        setBackBtn(true, View.OnClickListener {
            finish()
        })
        setBBTitle(R.string.comment_detail_title)

        replyCommentET = findViewById(R.id.reply_comment_et)
        replyPostTV = findViewById(R.id.reply_post_tv)

        replyPostTV.setOnClickListener({
            var replyContent = replyCommentET.text.toString().trim()
            if (replyContent.isNotEmpty()) {
                var netRequest = NetRequest(ProtocolConstants.FunId.FuncReplyComment, ProtocolConstants.URI.DataBin)
                var replyCommentRequest = BBReplyCommentRequest()
                replyCommentRequest.CommentReply.Uin = BBCore.Instance.accountCore.loginService.getUIN()
                replyCommentRequest.CommentReply.CommentId = comment.Id
                replyCommentRequest.CommentReply.Content = replyContent
                replyCommentRequest.CommentReply.ReplyUin = replyUin
                replyUin = 0L
                netRequest.body = JSON.toJSONString(replyCommentRequest)
                BBCore.Instance.netCore.startRequestAsync(netRequest, replyNetListener)
                replyCommentET.setText("")
                replyCommentET.clearFocus()
                hideVKB()
            }
        })
        if (needKeyboard) {
            BBCore.Instance.uiHandler.postDelayed(Runnable {
                replyCommentET.requestFocus()
                showVKB(replyCommentET)
            }, 500)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter?.refreshCommentReplyList(comment.Id)
    }

    lateinit var avatarIV: SimpleDraweeView
    lateinit var nickNameTV: TextView
    lateinit var companyNameTV: TextView
    lateinit var insuranceTypeNameTV: TextView
    lateinit var contentTV: TextView
    lateinit var scoreLayout: LinearLayout
    lateinit var viewTV: TextView
    lateinit var upTV: TextView
    lateinit var replyTV: TextView
    lateinit var likeIV: ImageView
    lateinit var likeTV: TextView
    lateinit var commentLayout: View
    lateinit var likeLayout: View
    lateinit var timeTV: TextView
    lateinit var scoreTV: TextView
    lateinit var replyCommentET: EditText
    lateinit var replyPostTV: TextView
    var starsIV = arrayOfNulls<ImageView?>(5)
    override fun getHeaderView(): View? {
        var header = layoutInflater.inflate(R.layout.comment_detail_header, null)
        avatarIV = header.findViewById(R.id.avatar_iv)
        nickNameTV = header.findViewById(R.id.nickname_tv)
        companyNameTV = header.findViewById(R.id.company_name_tv)
        insuranceTypeNameTV = header.findViewById(R.id.insurance_type_name_tv)
        timeTV = header.findViewById(R.id.time_tv)
        scoreTV = header.findViewById(R.id.score_tv)
        viewTV = header.findViewById(R.id.view_tv)
        scoreLayout = header.findViewById(R.id.detail_score_layout)
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

    var needKeyboard = false
    lateinit var comment: BBComment
    var contactEntity: ContactEntity? = null
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
        companyNameTV.text = comment.CompanyName
        insuranceTypeNameTV.text = comment.InsuranceTypeName
        scoreTV.text = getString(R.string.comment_star_info, scoreIndex)
        timeTV.text = TimeUtil.formatTimeInList(this, comment.Timestamp)
        contentTV.text = comment.Content.trim()
        viewTV.text = getString(R.string.comment_view_info, comment.ViewCount)
        upTV.text = getString(R.string.comment_up_info, comment.UpCount)
        replyTV.text = getString(R.string.comment_reply_info, comment.ReplyCount)

        scoreLayout.removeAllViews()
        if (comment.Score1 != 0) {
            scoreLayout.addView(getDetailScoreLayout(comment.Score1, R.string.comment_sub_1_colons))
        }

        if (comment.Score2 != 0) {
            scoreLayout.addView(getDetailScoreLayout(comment.Score2, R.string.comment_sub_2_colons))
        }

        if (comment.Score3 != 0) {
            scoreLayout.addView(getDetailScoreLayout(comment.Score3, R.string.comment_sub_3_colons))
        }

        if (comment.Score4 != 0) {
            scoreLayout.addView(getDetailScoreLayout(comment.Score4, R.string.comment_sub_4_colons))
        }

        likeLayout.setOnClickListener({
            var netRequest = NetRequest(ProtocolConstants.FunId.FuncUpComment, ProtocolConstants.URI.DataBin)
            var upCommentRequest = BBUpCommentRequest()
            upCommentRequest.CommentUp.Uin = BBCore.Instance.accountCore.loginService.getUIN()
            upCommentRequest.CommentUp.CommentId = comment.Id
            upCommentRequest.IsUp = !comment.IsUp
            netRequest.body = JSON.toJSONString(upCommentRequest)
            BBCore.Instance.netCore.startRequestAsync(netRequest, upNetListener)
        })

        commentLayout.setOnClickListener({
            replyCommentET.requestFocus()
            showVKB(replyCommentET)
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

    private var replyNetListener = object : NetListener {
        override fun onNetDoneInMainThread(netRequest: NetRequest, netResponse: NetResponse) {
            if (netResponse.respCode == 200) {
                var replyCommentResponse = JSON.parseObject(netResponse.bbResp.Body.toString(),
                        BBReplyCommentResponse::class.java)
                comment = replyCommentResponse.Comment
                updateHeaderView()
                adapter?.refreshCommentReplyList(comment.Id)
            }
        }

        override fun onNetDoneInSubThread(netRequest: NetRequest, netResponse: NetResponse) {
        }

        override fun onNetTaskCancel(netRequest: NetRequest) {
        }
    }

    private fun getDetailScoreLayout(score: Int, titleId: Int): View {
        var view = layoutInflater.inflate(R.layout.comment_detail_score_item_view, null)
        var scoreTitleTV = view.findViewById<TextView>(R.id.score_title_tv)
        var scoreDetailTV = view.findViewById<TextView>(R.id.score_desc_tv)
        scoreTitleTV.setText(titleId)
        scoreDetailTV.text = getScoreDesc(score)
        return view
    }

    private fun getScoreDesc(score: Int): String {
        when (score) {
            20 -> {
                return getString(R.string.comment_score_desc_4)
            }
            40 -> {
                return getString(R.string.comment_score_desc_3)
            }
            60 -> {
                return getString(R.string.comment_score_desc_3)
            }
            80 -> {
                return getString(R.string.comment_score_desc_2)
            }
            100 -> {
                return getString(R.string.comment_score_desc_1)
            }
        }
        return ""
    }
}