package com.liu.bloodpressure.util

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.liu.bloodpressure.application
import com.liu.bloodpressure.ui.act.StartActivity

class AppLifecycle :Application.ActivityLifecycleCallbacks{
    var runningActivities = 0
    var inBackgroundTime = 0L

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        "Lifecycle onActivity Created ${activity.localClassName}".logE()
    }

    override fun onActivityStarted(activity: Activity) {
        "Lifecycle onActivity Started ${activity.localClassName}".logE()
        if (runningActivities == 0
            && inBackgroundTime != 0L
            && System.currentTimeMillis() - inBackgroundTime > 5000
            ) {
            startStartActivity()
        }

        runningActivities++
    }

    private fun startStartActivity() {
        application.startActivity(Intent(application, StartActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    override fun onActivityResumed(activity: Activity) {
        "Lifecycle onActivity Resumed ${activity.localClassName}".logE()
    }

    override fun onActivityPaused(activity: Activity) {
        "Lifecycle onActivity Paused ${activity.localClassName}".logE()
    }

    override fun onActivityStopped(activity: Activity) {
        "Lifecycle onActivity Stopped ${activity.localClassName}".logE()
        runningActivities--
        if (runningActivities == 0) {
            // cold launcher
        }
        inBackgroundTime = System.currentTimeMillis()
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        "Lifecycle onActivity SaveInstanceState ${activity.localClassName}".logE()
    }

    override fun onActivityDestroyed(activity: Activity) {
        "Lifecycle onActivity Destroyed ${activity.localClassName}".logE()
    }
}