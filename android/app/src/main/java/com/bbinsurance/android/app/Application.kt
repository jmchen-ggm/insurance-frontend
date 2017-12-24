package com.bbinsurance.android.app

import android.app.Application
import android.content.Context
import com.bbinsurance.android.app.core.BBCore
import com.bbinsurance.android.lib.log.BBLog
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher
import com.facebook.imagepipeline.core.ImagePipelineConfig
import okhttp3.OkHttpClient

/**
 * Created by jiaminchen on 2017/10/23.
 */
class Application : Application() {
    private val TAG = "BB.Application"

    companion object {
        lateinit var ApplicationContext: Context
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ApplicationContext = base!!
        BBCore.initCore()

        BBLog.i(TAG, "attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()
        var okHttpClient = OkHttpClient()
        var config = ImagePipelineConfig
                .newBuilder(ApplicationContext)
                .setNetworkFetcher(OkHttpNetworkFetcher(okHttpClient))
                .build();
        Fresco.initialize(this, config)
        FLog.setMinimumLoggingLevel(FLog.INFO)
        FLog.setLoggingDelegate(LogDelegate())
    }
}