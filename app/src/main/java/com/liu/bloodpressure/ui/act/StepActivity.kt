package com.liu.bloodpressure.ui.act

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.liu.bloodpressure.R
import com.liu.bloodpressure.ui.adapter.StepAdapter
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.model.StepEntity

class StepActivity : BaseActivity() {
    private lateinit var mViewPager:ViewPager
    private lateinit var mNextButton: Button
    private lateinit var mTitleStep: View

    override fun contentLayout(): Int {
        return R.layout.activity_step
    }

    override fun initView() {
        mViewPager = findViewById(R.id.vp_step)
        mNextButton = findViewById(R.id.btn_next)
        mTitleStep = findViewById(R.id.title_step)
        mTitleStep.findViewById<TextView>(R.id.title_right_text).let {
            it.text = getString(R.string.step_skip)
            it.setOnClickListener {_->
                startMainActivity()
            }
        }
    }

    override fun initData() {
        mViewPager.adapter = StepAdapter(this, mutableListOf<StepEntity>().apply {
            add(StepEntity(getString(R.string.step1_title),getString(R.string.step1_content),R.mipmap.bg_step1))
            add(StepEntity(getString(R.string.step2_title),getString(R.string.step2_content),R.mipmap.bg_step2))
            add(StepEntity(getString(R.string.step3_title),getString(R.string.step3_content),R.mipmap.bg_step3))
        })

        mViewPager.addOnPageChangeListener(object :OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0,1 -> {
                        mNextButton.text = getString(R.string.step_next)
                    }
                    2->{
                        mNextButton.text = getString(R.string.step_start)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    override fun next(v: View) {
        when(mViewPager.currentItem){
            2->{
                startMainActivity()
            }
            1 ->{
                mViewPager.currentItem = 2
            }
            0->{
                mViewPager.currentItem = 1
            }
        }

    }
}