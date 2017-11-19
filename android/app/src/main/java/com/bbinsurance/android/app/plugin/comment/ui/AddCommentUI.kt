package com.bbinsurance.android.app.plugin.comment.ui

import android.app.ProgressDialog
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.protocol.Comment
import com.bbinsurance.android.app.ui.BaseActivity
import com.bbinsurance.android.lib.Util

/**
 * Created by jiaminchen on 17/11/19.
 */
class AddCommentUI : BaseActivity() {

    lateinit var starsIV: Array<ImageView?>
    lateinit var commentET: EditText
    lateinit var postBtn: Button
    var score = 0;
    var progressDialog: ProgressDialog? = null

    override fun initView() {
        super.initView()
        setTitle(R.string.add_comment_title)
        setBackBtn(true, View.OnClickListener { finish() })

        starsIV = arrayOfNulls<ImageView?>(5)
        starsIV[0] = findViewById(R.id.star_1_iv)
        starsIV[1] = findViewById(R.id.star_2_iv)
        starsIV[2] = findViewById(R.id.star_3_iv)
        starsIV[3] = findViewById(R.id.star_4_iv)
        starsIV[4] = findViewById(R.id.star_5_iv)

        for (i in starsIV.indices) {
            starsIV[i]?.tag = i
            starsIV[i]?.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    var index = view?.tag as Int
                    score = 20 * (index + 1)
                    var j = 0
                    while (j <= index) {
                        starsIV[j]?.setImageResource(R.drawable.star_yellow)
                        j++
                    }
                    while (j < starsIV.size) {
                        starsIV[j]?.setImageResource(R.drawable.star_gray)
                    }
                }
            })
        }

        commentET = findViewById(R.id.comment_et)
        postBtn = findViewById(R.id.post_btn)
        postBtn.setOnClickListener({
            var content = commentET.text.toString()
            if (!Util.isNullOrNil(content)) {
                progressDialog = ProgressDialog.show(this, "", "", true, false)
                BBCore.Instance.threadCore.post(Runnable {
                    var comment = Comment()
                    comment.Uin = BBCore.Instance.accountCore.getUIN()
                    comment.Content = content
                    comment.Timestamp = System.currentTimeMillis() / 1000
                    comment.Score = score
                    BBCore.Instance.dbCore.db.commentDao().insertComment(comment)
                    BBCore.Instance.commentCore.commentSyncService.startToUpload()
                    runOnUiThread({
                        progressDialog?.dismiss()
                    })
                })
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.add_comment_ui
    }
}