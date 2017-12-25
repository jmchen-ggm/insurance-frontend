package com.bbinsurance.android.app.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.plugin.comment.ui.CommentUI
import com.bbinsurance.android.app.plugin.learn.ui.LearnArticleUI
import com.bbinsurance.android.app.protocol.BBInsurance
import com.bbinsurance.android.app.ui.adapter.BannerAdapter
import com.bbinsurance.android.app.ui.component.BannerBaseUIComponent
import com.bigkoo.convenientbanner.ConvenientBanner
import com.facebook.drawee.view.SimpleDraweeView


/**
 * Created by jiaminchen on 17/11/12.
 */
class HomeFragmentUI : Fragment(), BannerBaseUIComponent<BBInsurance> {

    override fun getConvenientBanner(): ConvenientBanner<BBInsurance> {
        return convenientBanner
    }

    override fun getComponentContext(): Context {
        return context
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var convertView = inflater?.inflate(getLayoutId(), container, false)!!
        compareLayout = convertView.findViewById(R.id.compare_layout)
        compareLayout.setOnClickListener({
        })
        consultLayout = convertView.findViewById(R.id.consult_layout)
        consultLayout.setOnClickListener({
        })
        evaluateLayout = convertView.findViewById(R.id.evaluate_layout)
        evaluateLayout.setOnClickListener({
            var intent = Intent(context, CommentUI::class.java)
            startActivity(intent)
        })
        learnLayout = convertView.findViewById(R.id.learn_layout)
        learnLayout.setOnClickListener({
            var intent = Intent(context, LearnArticleUI::class.java)
            startActivity(intent)
        })

        convenientBanner = convertView.findViewById(R.id.convenientBanner)
        bannerAdapter = BannerAdapter(this)
        bannerAdapter.updateBannerList()
        convenientBanner.setPages(bannerAdapter, bannerAdapter.bannerList)
        convenientBanner.startTurning(2000)

        initCommentLayout(convertView)

        return convertView
    }

    private fun initCommentLayout(convertView : View) {
        hotCommentLayout = convertView.findViewById(R.id.hot_comment_layout)
        hotCommentHeaderLayout = convertView.findViewById(R.id.hot_comment_header_layout)
        hotCommentAvatarIV = convertView.findViewById(R.id.hot_comment_avatar_iv)
        hotCommentNickNameTV = convertView.findViewById(R.id.hot_comment_nickname_tv)
        hotCommentContentTV = convertView.findViewById(R.id.hot_comment_content_tv)
        hotCommentLikeCountTV = convertView.findViewById(R.id.hot_comment_like_count_tv)

        if (BBCore.Instance.accountCore.loginService.isLogin()) {
            hotCommentLayout.visibility = View.VISIBLE
            var contactEntity = BBCore.Instance.accountCore.loginService.getCurrentContactEntity()
            hotCommentAvatarIV.setImageURI(BBCore.Instance.accountCore.getContactThumbUrl(contactEntity))
            hotCommentNickNameTV.setText(contactEntity.Nickname)
            var likeCount = 377
            hotCommentLikeCountTV.text = likeCount.toString()
            hotCommentContentTV.text = "这个应用真心非常的不错，好用，很棒很棒"
            hotCommentHeaderLayout.setOnClickListener({
                var intent = Intent(context, CommentUI::class.java)
                startActivity(intent)
            })
        } else {
            hotCommentLayout.visibility = View.GONE
        }
    }

    fun getLayoutId(): Int {
        return R.layout.home_fragment_ui
    }

    private lateinit var compareLayout: View
    private lateinit var consultLayout: View
    private lateinit var evaluateLayout: View
    private lateinit var learnLayout: View
    private lateinit var convenientBanner: ConvenientBanner<BBInsurance>
    private lateinit var bannerAdapter : BannerAdapter

    private lateinit var hotCommentLayout : RelativeLayout
    private lateinit var hotCommentHeaderLayout : RelativeLayout
    private lateinit var hotCommentAvatarIV: SimpleDraweeView
    private lateinit var hotCommentNickNameTV : TextView
    private lateinit var hotCommentContentTV : TextView
    private lateinit var hotCommentLikeCountTV: TextView
}