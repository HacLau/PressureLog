package com.liu.bloodpressure.ui.act

import android.view.View
import android.widget.ProgressBar
import com.liu.bloodpressure.R
import com.liu.bloodpressure.base.BaseActivity
import java.util.Timer
import java.util.TimerTask


class SplashActivity : BaseActivity() {
    private lateinit var mProgressBar:ProgressBar
    override fun contentLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {

        setProgress()
    }

    override fun initView() {
        mProgressBar = findViewById(R.id.sp_progress)
    }

    private fun setProgress() {
        val time = Timer()
        time.schedule(object :TimerTask(){
            override fun run() {
                if (mProgressBar.progress == 100){
                    time.cancel()
                    next(View(this@SplashActivity))
                }else {
                    mProgressBar.progress++
                }
            }
        },33,33)
    }

    override fun next(v: View) {
        startStepActivity()
    }


}