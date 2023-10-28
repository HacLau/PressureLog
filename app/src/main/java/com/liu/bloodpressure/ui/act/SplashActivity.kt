package com.liu.bloodpressure.ui.act

import android.view.View
import android.widget.ProgressBar
import com.liu.bloodpressure.R
import com.liu.bloodpressure.advertising.AdvertisingHelper
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.util.SPHelper
import com.liu.bloodpressure.util.logE
import com.liu.bloodpressure.util.other
import com.liu.bloodpressure.util.yes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


class SplashActivity : BaseActivity() {
    private lateinit var mProgressBar: ProgressBar
    override fun contentLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        setProgress()
        AdvertisingHelper.launchAd.load(this)
        AdvertisingHelper.recordAd.load(this)
        AdvertisingHelper.historyAd.load(this)
        AdvertisingHelper.alarmAd.load(this)
    }

    override fun initView() {
        mProgressBar = findViewById(R.id.sp_progress)
    }

    private fun setProgress() {
        val time = Timer()
        time.schedule(object : TimerTask() {
            override fun run() {
                if (mProgressBar.progress > 50 && AdvertisingHelper.launchAd.cacheIsNotEmpty) {
                    mProgressBar.progress = mProgressBar.progress + 2
                } else if (mProgressBar.progress > 80 && AdvertisingHelper.launchAd.cacheIsNotEmpty) {
                    mProgressBar.progress = mProgressBar.progress + 3
                } else {
                    mProgressBar.progress++
                }

                if (mProgressBar.progress >= 100) {
                    time.cancel()
                    CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                        AdvertisingHelper.launchAd.showFullScreen(this@SplashActivity) {
                            next(View(this@SplashActivity))
                        }
                    }
                } else {
                    mProgressBar.progress++
                }
            }
        }, 33, 99)
    }

    override fun next(v: View) {
        SPHelper.isLaunchedStep.yes {
            startMainActivity()
        }.other {
            startStepActivity()
        }
    }


}