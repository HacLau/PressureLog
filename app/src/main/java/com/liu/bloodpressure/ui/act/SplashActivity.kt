package com.liu.bloodpressure.ui.act

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import com.liu.bloodpressure.R
import com.liu.bloodpressure.advertising.AdvertisingHelper
import com.liu.bloodpressure.advertising.AdvertisingType
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.net.FireBaseHelper
import com.liu.bloodpressure.util.SPHelper
import com.liu.bloodpressure.util.logE
import com.liu.bloodpressure.util.no
import com.liu.bloodpressure.util.other
import com.liu.bloodpressure.util.yes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
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
        AdvertisingHelper.overCount().no {
            FireBaseHelper.updateEvent("pl_ad_chance", mutableMapOf(
                "pl_ad_id" to AdvertisingType.LAUNCH.type
            ))
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun initView() {
        mProgressBar = findViewById(R.id.sp_progress)
    }

    private fun setProgress() {
        val time = Timer()
        time.schedule(object : TimerTask() {
            override fun run() {
                if (!isActivityOnResume()) {
                    return
                }
                "AdvertisingHelper.launchAd.cacheIsNotEmpty  ${AdvertisingHelper.launchAd.cache.isNotEmpty()}".logE()
                if (AdvertisingHelper.launchAd.cache.isNotEmpty()){
                    if (mProgressBar.progress > 40) {
                        mProgressBar.progress = mProgressBar.progress + 4
                    } else if (mProgressBar.progress > 70) {
                        mProgressBar.progress = mProgressBar.progress + 5
                    } else {
                        mProgressBar.progress = mProgressBar.progress + 3
                    }
                }else{
                    mProgressBar.progress++
                }


                if (mProgressBar.progress >= 100) {
                    time.cancel()
                    CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                        AdvertisingHelper.launchAd.showFullScreen(this@SplashActivity) {
                            lifecycleScope.launch {
                                if (isActivityOnResume()) {
                                    next(View(this@SplashActivity))
                                } else {

                                }

                            }
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