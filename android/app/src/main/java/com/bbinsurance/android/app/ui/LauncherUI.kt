package com.bbinsurance.android.app.ui

import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.bbinsurance.android.app.AppConstants
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.app.plugin.account.ui.LoginUI
import com.bbinsurance.android.app.plugin.comment.CommentLogic
import com.bbinsurance.android.app.plugin.comment.ui.AddCommentUI
import com.bbinsurance.android.app.ui.fragment.*
import com.bbinsurance.android.lib.util.PermissionUtil

/**
 * Created by jiaminchen on 2017/10/23.
 */
class LauncherUI : BaseActivity(), BottomNavigationBar.OnTabSelectedListener {

    private lateinit var bottomNavigationBar: BottomNavigationBar

    override fun getLayoutId(): Int {
        return R.layout.launcher_ui
    }

    private lateinit var launcherHeader: View
    private lateinit var searchET: EditText
    private lateinit var commentView: View
    private var homeFragment: Fragment? = null
    private var discoverFragment: Fragment? = null
    private var myFragment: Fragment? = null
    private var messageFragment: Fragment? = null

    override fun initView() {
        super.initView()
        launcherHeader = findViewById(R.id.launcher_header)
        commentView = findViewById(R.id.comment_layout)
        searchET = findViewById(R.id.search_et)
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar)
        bottomNavigationBar
                .addItem(BottomNavigationItem(R.drawable.tab_home_icon, R.string.tab_home).setActiveColorResource(R.color.main_blue_color))
                .addItem(BottomNavigationItem(R.drawable.tab_discover_icon, R.string.tab_discover).setActiveColorResource(R.color.main_blue_color))
                .addItem(BottomNavigationItem(R.drawable.tab_add_icon, R.string.tab_add).setActiveColorResource(R.color.main_blue_color))
                .addItem(BottomNavigationItem(R.drawable.tab_message_icon, R.string.tab_message).setActiveColorResource(R.color.main_blue_color))
                .addItem(BottomNavigationItem(R.drawable.tab_my_icon, R.string.tab_my).setActiveColorResource(R.color.main_blue_color))
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED_NO_TITLE)
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        bottomNavigationBar.initialise()
        bottomNavigationBar.setTabSelectedListener(this)

        replaceFragments(0)
        commentView.setOnClickListener({
            if (BBCore.Instance.accountCore.loginService.isLogin()) {
                var intent = Intent(this, AddCommentUI::class.java)
                startActivity(intent)
            } else {
                var intent = Intent(this, LoginUI::class.java)
                startActivity(intent)
            }
        })
    }

    private val REQUEST_PERMISSION_CODE = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (permission in AppConstants.REQUST_PERMISSION) {
            PermissionUtil.verifyPermissions(this, permission, REQUEST_PERMISSION_CODE)
        }
    }

    override fun onTabReselected(position: Int) {
    }

    override fun onTabUnselected(position: Int) {
    }

    override fun onTabSelected(position: Int) {
        replaceFragments(position);
    }

    private var currentFragmentIndex = 0
    private fun replaceFragments(position: Int) {
        supportFragmentManager.beginTransaction().apply {
            when (position) {
                0 -> {
                    currentFragmentIndex = 0
                    if (homeFragment == null) {
                        homeFragment = HomeFragmentUI()
                    }
                    launcherHeader.visibility = View.VISIBLE
                    replace(R.id.home_activity_frag_container, homeFragment)
                }
                1 -> {
                    currentFragmentIndex = 1
                    if (discoverFragment == null) {
                        discoverFragment = DiscoverFragmentUI()
                    }
                    launcherHeader.visibility = View.VISIBLE
                    replace(R.id.home_activity_frag_container, discoverFragment)
                }
                2 -> {
                    showAddDialog()
                }
                3 -> {
                    currentFragmentIndex = 3
                    if (messageFragment == null) {
                        messageFragment = MessageFragmentUI()
                    }
                    launcherHeader.visibility = View.GONE
                    replace(R.id.home_activity_frag_container, messageFragment)
                }
                4 -> {
                    currentFragmentIndex = 4
                    if (myFragment == null) {
                        myFragment = MyFragmentUI()
                    }
                    launcherHeader.visibility = View.GONE
                    replace(R.id.home_activity_frag_container, myFragment)
                }
            }
        }.commitAllowingStateLoss()
    }

    override fun needActionBar(): Boolean {
        return false
    }

    private var addDialog: BottomSheetDialog? = null
    private fun showAddDialog() {
        if (addDialog == null) {
            addDialog = BottomSheetDialog(this)
            var addDialogView = LayoutInflater.from(this).inflate(R.layout.add_dialog_ui, null)
            var textBtn : View = addDialogView.findViewById(R.id.text_btn)
            var askBtn : View = addDialogView.findViewById(R.id.ask_btn)
            var commentBtn : View = addDialogView.findViewById(R.id.comment_btn)
            textBtn.setOnClickListener(dialogClickListener)
            askBtn.setOnClickListener(dialogClickListener)
            commentBtn.setOnClickListener(dialogClickListener)
            addDialog?.setContentView(addDialogView)
        }
        addDialog?.show()
        addDialog?.setOnDismissListener({
            bottomNavigationBar.selectTab(currentFragmentIndex)
        })
    }
    private var dialogClickListener = View.OnClickListener { view ->
        var scaleXAnim = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f).setDuration(400);
        scaleXAnim.interpolator = AccelerateDecelerateInterpolator()
        var scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f).setDuration(400);
        scaleYAnim.interpolator = AccelerateDecelerateInterpolator()
        scaleXAnim.start()
        scaleYAnim.start()
        CommentLogic.goToAddCommentUI(this, Intent())
        addDialog?.dismiss()
    }
}
