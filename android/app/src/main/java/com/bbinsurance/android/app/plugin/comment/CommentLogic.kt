package com.bbinsurance.android.app.plugin.comment

import android.content.Context
import android.content.Intent
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.plugin.account.ui.LoginUI
import com.bbinsurance.android.app.plugin.comment.ui.AddCommentUI
import com.bbinsurance.android.app.plugin.comment.ui.CommentDetailUI

/**
 * Created by jiaminchen on 18/2/2.
 */
class CommentLogic {

    companion object {
        fun goToAddCommentUI(context : Context, intent : Intent) {
            if (BBCore.Instance.accountCore.loginService.isLogin()) {
                intent.setClass(context, AddCommentUI::class.java)
                context.startActivity(intent)
            } else {
                intent.setClass(context, LoginUI::class.java)
                context.startActivity(intent)
            }
        }

        fun goToCommentDetailUI(context : Context, intent : Intent) {
            if (BBCore.Instance.accountCore.loginService.isLogin()) {
                intent.setClass(context, CommentDetailUI::class.java)
                context.startActivity(intent)
            } else {
                intent.setClass(context, LoginUI::class.java)
                context.startActivity(intent)
            }
        }
    }
}