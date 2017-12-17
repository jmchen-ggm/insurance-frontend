package com.bbinsurance.android.app.plugin.comment.ui

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.db.entity.CommentEntity
import com.bbinsurance.android.app.ui.BaseActivity
import com.bbinsurance.android.lib.Util

/**
 * Created by jiaminchen on 17/11/19.
 */
class AddCommentUI : BaseActivity() {

    lateinit var totalStarsIV: Array<ImageView?>
    lateinit var subStars1TV: Array<ImageView?>
    lateinit var subStars2TV: Array<ImageView?>
    lateinit var subStars3TV: Array<ImageView?>

    lateinit var totalStarClickListener : StarIVClickListener
    lateinit var subStar1ClickListner : StarIVClickListener
    lateinit var subStar2ClickListner : StarIVClickListener
    lateinit var subStar3ClickListner : StarIVClickListener

    lateinit var commentET: EditText
    lateinit var postBtn: Button

    override fun initView() {
        super.initView()
        setBBTitle(R.string.add_comment_title)
        setBackBtn(true, View.OnClickListener { finish() })

        totalStarsIV = arrayOfNulls<ImageView?>(5)
        totalStarsIV[0] = findViewById(R.id.star_1_iv)
        totalStarsIV[1] = findViewById(R.id.star_2_iv)
        totalStarsIV[2] = findViewById(R.id.star_3_iv)
        totalStarsIV[3] = findViewById(R.id.star_4_iv)
        totalStarsIV[4] = findViewById(R.id.star_5_iv)
        totalStarClickListener = StarIVClickListener(totalStarsIV, R.drawable.star_gray, R.drawable.star_yellow)
        initIVArrays(totalStarsIV, totalStarClickListener)

        subStars1TV = arrayOfNulls<ImageView?>(5)
        subStars1TV[0] = findViewById(R.id.star_sub_1_1_iv)
        subStars1TV[1] = findViewById(R.id.star_sub_1_2_iv)
        subStars1TV[2] = findViewById(R.id.star_sub_1_3_iv)
        subStars1TV[3] = findViewById(R.id.star_sub_1_4_iv)
        subStars1TV[4] = findViewById(R.id.star_sub_1_5_iv)
        subStar1ClickListner = StarIVClickListener(subStars1TV, R.drawable.sub_star_gray, R.drawable.sub_star_orange)
        initIVArrays(subStars1TV, subStar1ClickListner)

        subStars2TV = arrayOfNulls<ImageView?>(5)
        subStars2TV[0] = findViewById(R.id.star_sub_2_1_iv)
        subStars2TV[1] = findViewById(R.id.star_sub_2_2_iv)
        subStars2TV[2] = findViewById(R.id.star_sub_2_3_iv)
        subStars2TV[3] = findViewById(R.id.star_sub_2_4_iv)
        subStars2TV[4] = findViewById(R.id.star_sub_2_5_iv)
        subStar2ClickListner = StarIVClickListener(subStars2TV, R.drawable.sub_star_gray, R.drawable.sub_star_orange)
        initIVArrays(subStars2TV, subStar2ClickListner)

        subStars3TV = arrayOfNulls<ImageView?>(5)
        subStars3TV[0] = findViewById(R.id.star_sub_3_1_iv)
        subStars3TV[1] = findViewById(R.id.star_sub_3_2_iv)
        subStars3TV[2] = findViewById(R.id.star_sub_3_3_iv)
        subStars3TV[3] = findViewById(R.id.star_sub_3_4_iv)
        subStars3TV[4] = findViewById(R.id.star_sub_3_5_iv)
        subStar3ClickListner = StarIVClickListener(subStars3TV, R.drawable.sub_star_gray, R.drawable.sub_star_orange)
        initIVArrays(subStars3TV, subStar3ClickListner)

        commentET = findViewById(R.id.comment_et)
        postBtn = findViewById(R.id.post_btn)
        postBtn.setOnClickListener({
            var content = commentET.text.toString()
            if (!Util.isNullOrNil(content)) {
                BBCore.Instance.threadCore.post(Runnable {
                    var comment = CommentEntity()
                    comment.Uin = BBCore.Instance.accountCore.loginService.getUIN()
                    comment.Content = content
                    comment.Timestamp = System.currentTimeMillis()
                    comment.TotalScore = totalStarClickListener.score
                    comment.Score1 = subStar1ClickListner.score
                    comment.Score2 = subStar2ClickListner.score
                    comment.Score3 = subStar3ClickListner.score
                    BBCore.Instance.dbCore.db.commentDao().insertComment(comment)
                    BBCore.Instance.commentCore.syncService.startToUpload()
                    runOnUiThread({
                        finish()
                    })
                })
            }
        })
    }

    fun initIVArrays(ivs : Array<ImageView?>, listener : View.OnClickListener) {
        for (i in ivs.indices) {
            ivs[i]?.tag = i
            ivs[i]?.setOnClickListener(listener)
        }
    }

    class StarIVClickListener : View.OnClickListener {

        var starsIV: Array<ImageView?>
        var initRes : Int
        var clickRes : Int
        var score : Int

        constructor(starsIV: Array<ImageView?>, initRes : Int, clickRes : Int) {
            this.starsIV = starsIV
            this.initRes = initRes
            this.clickRes = clickRes
            this.score = 0
        }

        override fun onClick(view: View?) {
            var index = view?.tag as Int
            score = (index + 1) * 20
            var j = 0
            while (j <= index) {
                this.starsIV[j]?.setImageResource(clickRes)
                j++
            }
            while (j < this.starsIV.size) {
                this.starsIV[j]?.setImageResource(initRes)
                j++
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.add_comment_ui
    }
}