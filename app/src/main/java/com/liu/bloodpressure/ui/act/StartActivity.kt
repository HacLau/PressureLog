package com.liu.bloodpressure.ui.act

import android.view.View
import android.widget.CheckBox
import com.liu.bloodpressure.R
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.util.toast

class StartActivity : BaseActivity() {
    private lateinit var mCheckBox:CheckBox
    override fun contentLayout(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
        mCheckBox = findViewById(R.id.start_check)
    }

    override fun initData() {

    }

    override fun next(v: View) {
        if(mCheckBox.isChecked) {
            startSplashActivity()
        }else{
            resources.getString(R.string.start_check_toast).toast(this)
        }
    }
}