package com.liu.bloodpressure.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.liu.bloodpressure.model.News
import com.liu.bloodpressure.model.Record
import com.liu.bloodpressure.ui.act.MainActivity
import com.liu.bloodpressure.ui.act.RecordMoreActivity
import com.liu.bloodpressure.ui.act.RecordNewActivity
import com.liu.bloodpressure.ui.act.SplashActivity
import com.liu.bloodpressure.ui.act.StepActivity
import com.liu.bloodpressure.ui.act.WebViewActivity
import com.liu.bloodpressure.util.SPHelper
import com.liu.bloodpressure.util.type.IntentName
import com.liu.bloodpressure.util.type.PageType

abstract class BaseActivity : AppCompatActivity() {
    var isResume = false
    private val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setFullScreen()
        super.onCreate(savedInstanceState)
        isResume = false
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setCustomDensity()
        setContentView(contentLayout())
        initView()
        initData()
    }
    private fun setCustomDensity() {
        val metrics = resources.displayMetrics
        (metrics.heightPixels / 760f).let {
            metrics.density = it
            metrics.scaledDensity = it
            metrics.densityDpi = (160 * it).toInt()
        }
    }

    private fun setFullScreen() {
//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//        window.statusBarColor = getColor(R.color.black)
    }

    abstract fun contentLayout(): Int
    abstract fun initView()
    abstract fun initData()

    fun startSplashActivity() {
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

    fun startStepActivity() {
        startActivity(Intent(this, StepActivity::class.java))
        finish()
    }

    fun startMainActivity() {
        SPHelper.isLaunchedStep = true
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun startRecordNewActivity(pageType: String = PageType.new, record: Record? = null) {
        startActivity(Intent(this, RecordNewActivity::class.java).apply {
            putExtra(IntentName.pagType, pageType)
            putExtra(IntentName.record, record)
        })
    }

    fun startRecordMoreActivity() {
        startActivity(Intent(this, RecordMoreActivity::class.java))
    }

    fun startWebContentActivity(pageType: String, news: News? = null, url: String? = null, title: String? = null) {
        startActivity(Intent(this, WebViewActivity::class.java).apply {
            putExtra(IntentName.pagType, pageType)
            putExtra(IntentName.content, news)
            putExtra(IntentName.url, url)
            putExtra(IntentName.title, title)
        })
    }

    open fun next(v: View) {}
    override fun onStart() {
        super.onStart()
        isResume = false
    }

    override fun onResume() {
        super.onResume()
        isResume = true
    }

    override fun onPause() {
        super.onPause()
        isResume = false
    }

    override fun onStop() {
        super.onStop()
        isResume = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isResume = false
    }
    fun isActivityOnResume():Boolean{
        return isResume && ifLifecycleResume()
    }
    private fun ifLifecycleResume():Boolean{
        return Lifecycle.State.RESUMED == lifecycle.currentState
    }
}