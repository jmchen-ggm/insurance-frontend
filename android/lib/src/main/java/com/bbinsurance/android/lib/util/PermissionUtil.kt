package com.bbinsurance.android.lib.util

import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.app.Activity
import com.bbinsurance.android.lib.log.BBLog


/**
 * Created by jiaminchen on 2017/10/24.
 */
open class PermissionUtil {
    companion object {
        val TAG = "BB.PermissionUtil";
        fun verifyPermissions(activity: Activity, permission : String, requestCode : Int) {
            try {
                //检测是否有写的权限

                val permissionRet = ActivityCompat.checkSelfPermission(activity, permission)
                if (permissionRet != PackageManager.PERMISSION_GRANTED) {
                    // 没有写的权限，去申请写的权限，会弹出对话框
                    var permissionArray = arrayOf("", permission)
                    ActivityCompat.requestPermissions(activity, permissionArray, requestCode)
                }
            } catch (e: Exception) {
                BBLog.e(TAG, e, "verifyPermissions")
            }
        }

        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
            BBLog.i(TAG, "onRequestPermissionsResult requestCode=%d", requestCode)
            for (i in permissions!!.indices) {
                BBLog.i(TAG, "permissions: %s grantResult", permissions!![i], grantResults!![i])
            }
        }
    }
}