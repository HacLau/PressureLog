package com.liu.bloodpressure.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.liu.bloodpressure.R
import com.liu.bloodpressure.model.News
import com.liu.bloodpressure.model.Record
import com.liu.bloodpressure.ui.act.MainActivity
import com.liu.bloodpressure.ui.act.RecordMoreActivity
import com.liu.bloodpressure.ui.act.RecordNewActivity
import com.liu.bloodpressure.ui.act.SplashActivity
import com.liu.bloodpressure.ui.act.StepActivity
import com.liu.bloodpressure.ui.act.WebViewActivity
import com.liu.bloodpressure.util.IntentName
import com.liu.bloodpressure.util.PageType

abstract class BaseActivity : AppCompatActivity() {
    var country: String = "en"
    private val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setFullScreen()
        super.onCreate(savedInstanceState)
        setContentView(contentLayout())
        initView()
        initData()
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
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun startRecordNewActivity(pageType: String = PageType.new,record: Record? = null) {
        startActivity(Intent(this, RecordNewActivity::class.java).apply {
            putExtra(IntentName.pagType,pageType)
            putExtra(IntentName.record,record)
        })
    }

    fun startRecordMoreActivity() {
        startActivity(Intent(this, RecordMoreActivity::class.java))
    }

    fun startWebContentActivity(pageType: String, news: News? = null, url: String? = null,title:String? = null) {
        startActivity(Intent(this, WebViewActivity::class.java).apply {
            putExtra(IntentName.pagType, pageType)
            putExtra(IntentName.content, news)
            putExtra(IntentName.url, url)
            putExtra(IntentName.title, title)
        })
    }

    open fun next(v: View) {}


}