package com.liu.bloodpressure.util

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.liu.bloodpressure.application
import com.liu.bloodpressure.ui.act.SplashActivity
import com.liu.bloodpressure.ui.act.StartActivity

class AppLifecycle :Application.ActivityLifecycleCallbacks{
    var runningActivities = 0
    var inBackgroundTime = 0L

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        if (runningActivities == 0 ){
            if (!SPHelper.isLaunchedStart){
                startStartActivity()
            }else if (System.currentTimeMillis() - inBackgroundTime > 5000) {
                startSplashActivity()
            }
        }

        runningActivities++
    }

    private fun startSplashActivity() {
        application.startActivity(Intent(application, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun startStartActivity() {
        application.startActivity(Intent(application, StartActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        runningActivities--
        if (runningActivities == 0) {
            // cold launcher
        }
        inBackgroundTime = System.currentTimeMillis()
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}