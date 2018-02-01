package com.bbinsurance.android.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.plugin.account.ui.LoginUI
import com.bbinsurance.android.app.plugin.comment.CommentLogic

/**
 * Created by jiaminchen on 17/12/26.
 */
class AddFragmentUI : Fragment() {

    lateinit var commentBtn : Button
    lateinit var askBtn : Button

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater?.inflate(R.layout.add_fragment_ui, container, false)!!
        commentBtn = view.findViewById(R.id.comment_btn)
        askBtn = view.findViewById(R.id.ask_btn)

        commentBtn.setOnClickListener({
            CommentLogic.goToAddCommentUI(context, Intent());
        })
        askBtn.setOnClickListener({
            CommentLogic.goToAddCommentUI(context, Intent());
        })

        return view
    }
}