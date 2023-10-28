package com.liu.bloodpressure.ui.act

import android.content.res.ColorStateList
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.liu.bloodpressure.R
import com.liu.bloodpressure.advertising.AdvertisingHelper
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.database.RecordDataBase
import com.liu.bloodpressure.model.Record
import com.liu.bloodpressure.ui.view.DateAndTimePopupWindow
import com.liu.bloodpressure.ui.view.HorizontalPicker
import com.liu.bloodpressure.ui.view.TitleView
import com.liu.bloodpressure.util.DateKt
import com.liu.bloodpressure.util.type.IntentName
import com.liu.bloodpressure.util.type.PageType
import com.liu.bloodpressure.util.dp2px
import com.liu.bloodpressure.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RecordNewActivity : BaseActivity() {
    private lateinit var mTitleView: TitleView
    private lateinit var mTime: TextView
    private lateinit var mClTime: ConstraintLayout
    private lateinit var mSystolicPicker: HorizontalPicker
    private lateinit var mSystolicScale: ImageView
    private lateinit var mDiastolicPicker: HorizontalPicker
    private lateinit var mDiastolicScale: ImageView
    private lateinit var mDegreeTitle: TextView
    private lateinit var mDegreeContent: TextView
    private lateinit var mDegreeDes: TextView
    private lateinit var mDegreeScale: ImageView
    private lateinit var mSave: AppCompatButton

    private var currentColor: Int = R.color.degree_0

    private lateinit var mRecord: Record
    private var pageType: String? = null
    override fun contentLayout(): Int {
        return R.layout.activity_record_new
    }

    override fun initView() {
        mTitleView = findViewById(R.id.title)
        mTime = findViewById(R.id.time)
        mClTime = findViewById(R.id.cl_time)
        mSystolicPicker = findViewById(R.id.systolic_picker)
        mSystolicScale = findViewById(R.id.systolic_scale)
        mDiastolicPicker = findViewById(R.id.diastolic_picker)
        mDiastolicScale = findViewById(R.id.diastolic_scale)
        mDegreeTitle = findViewById(R.id.degree_title)
        mDegreeContent = findViewById(R.id.degree_content)
        mDegreeDes = findViewById(R.id.degree_des)
        mDegreeScale = findViewById(R.id.degree_scale)
        mSave = findViewById(R.id.record_new_save)
    }

    override fun initData() {
        pageType = intent.getStringExtra(IntentName.pagType)
        mRecord = intent.getParcelableExtra(IntentName.record) ?: Record(
            systolic = 90,
            diastolic = 60,
            degree = 1,
            showTime = System.currentTimeMillis()
        )

        mTitleView.leftImage.setOnClickListener {
            finish()
        }
        mClTime.setOnClickListener {
            createDialog()
        }
        mTime.text = DateKt.getDate(mRecord.showTime)

        setSystolic()
        setDiastolic()
        mSave.setOnClickListener {
            if (mRecord.diastolic > mRecord.systolic) {
                getString(R.string.toast_record_new).toast(this)
            } else {
                AdvertisingHelper.recordAd.showFullScreen(this){
                    CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                        kotlin.runCatching {
                            saveRecordData()
                        }
                    }
                    finish()
                }
            }
        }
    }

    private fun saveRecordData() {
        RecordDataBase.getDatabase(context = this).recordDao().let {
            when (pageType) {
                PageType.new -> {
                    it.insertRecord(mRecord)
                }

                PageType.edit -> {
                    it.updateRecord(mRecord)
                }
            }
        }
    }

    private fun createDialog() {
        DateAndTimePopupWindow(context = this, currTime = mRecord.showTime, clickSure = {
            mRecord.showTime = it
            mTime.text = DateKt.getDate(it)
        }).showAtLocation(findViewById(R.id.record_new_parent), Gravity.BOTTOM, 0, 0)
//        window.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.bg_btn_add_new_record))
    }

    private fun setSystolic() {
        getMutList().let {
            mSystolicPicker.setData(it, it.indexOf(mRecord.systolic.toString()))
        }
        systolicSelected(mRecord.systolic.toString(), 0)
        mSystolicPicker.onSelect = ::systolicSelected
        mSystolicPicker.onMove = ::systolicSelected
    }

    private fun getMutList(): List<String> {
        return mutableListOf<String>().apply {
            (20..300).forEach {
                add(it.toString())
            }
        }
    }

    private fun systolicSelected(value: String, index: Int) {
        mRecord.systolic = value.toInt()
        val color = when (value.toInt()) {
            in 20..<90 -> {
                R.color.degree_0
            }

            in 90..<120 -> {
                R.color.degree_1
            }

            in 120..<130 -> {
                R.color.degree_2
            }

            in 130..<140 -> {
                R.color.degree_3
            }

            in 140..180 -> {
                R.color.degree_4
            }

            in 181..300 -> {
                R.color.degree_5
            }

            else -> {
                currentColor
            }
        }
        setScale(mSystolicScale, color)
        setSelectedColor(mDiastolicPicker, color)
    }

    private fun setDiastolic() {
        getMutList().let {
            mDiastolicPicker.setData(it, it.indexOf(mRecord.diastolic.toString()))
        }

        diastolicSelected(mRecord.diastolic.toString(), 0)
        mDiastolicPicker.onSelect = ::diastolicSelected
        mDiastolicPicker.onMove = ::diastolicSelected
    }

    private fun diastolicSelected(value: String, index: Int) {
        mRecord.diastolic = value.toInt()
        val color = when (value.toInt()) {
            in 20..<60 -> {
                when (mRecord.systolic) {
                    in 20..<90 -> R.color.degree_0
                    else -> currentColor
                }
            }

            in 60..<80 -> {
                when (mRecord.systolic) {
                    in 90..119 -> {
                        R.color.degree_1
                    }

                    in 120..129 -> {
                        R.color.degree_2
                    }

                    else -> {
                        currentColor
                    }
                }
            }

            in 80..<90 -> {
                when (mRecord.systolic) {
                    in 130..<140 -> R.color.degree_3
                    else -> currentColor
                }
            }

            in 90..120 -> {
                when (mRecord.systolic) {
                    in 140..<180 -> R.color.degree_4
                    else -> currentColor
                }
            }

            in 121..300 -> {
                when (mRecord.systolic) {
                    in 181..320 -> R.color.degree_5
                    else -> currentColor
                }
            }

            else -> {
                currentColor
            }
        }
        setScale(mDiastolicScale, color)
        setSelectedColor(mDiastolicPicker, color)
    }


    private fun setScale(view: ImageView, degree: Int) {
        ColorStateList.valueOf(ContextCompat.getColor(this, degree)).let {
            view.imageTintList = it
            mDegreeScale.imageTintList = it
        }
        currentColor = degree
        when (degree) {
            R.color.degree_0 -> {
                mRecord.degree = 0
                mDegreeTitle.text = getString(R.string.degree_title_hypotension)
                mDegreeContent.text = getString(R.string.degree_content_hypotension)
                mDegreeDes.text = getString(R.string.degree_des_hypotension)
                mDegreeScale.translationX = (-132f).dp2px(this)
            }

            R.color.degree_1 -> {
                mRecord.degree = 1
                mDegreeTitle.text = getString(R.string.degree_title_normal)
                mDegreeContent.text = getString(R.string.degree_content_normal)
                mDegreeDes.text = getString(R.string.degree_des_normal)
                mDegreeScale.translationX = (-79.2f).dp2px(this)
            }

            R.color.degree_2 -> {
                mRecord.degree = 2
                mDegreeTitle.text = getString(R.string.degree_title_elevated)
                mDegreeContent.text = getString(R.string.degree_content_elevated)
                mDegreeDes.text = getString(R.string.degree_des_elevated)
                mDegreeScale.translationX = (-26.4f).dp2px(this)
            }

            R.color.degree_3 -> {
                mRecord.degree = 3
                mDegreeTitle.text = getString(R.string.degree_title_hs1)
                mDegreeContent.text = getString(R.string.degree_content_hs1)
                mDegreeDes.text = getString(R.string.degree_des_hs1)
                mDegreeScale.translationX = (+26.4f).dp2px(this)
            }

            R.color.degree_4 -> {
                mRecord.degree = 4
                mDegreeTitle.text = getString(R.string.degree_title_hs2)
                mDegreeContent.text = getString(R.string.degree_content_hs2)
                mDegreeDes.text = getString(R.string.degree_des_hs2)
                mDegreeScale.translationX = (+79.2f).dp2px(this)
            }

            R.color.degree_5 -> {
                mRecord.degree = 5
                mDegreeTitle.text = getString(R.string.degree_title_crisis)
                mDegreeContent.text = getString(R.string.degree_content_crisis)
                mDegreeDes.text = getString(R.string.degree_des_crisis)
                mDegreeScale.translationX = (+132f).dp2px(this)
            }
        }
    }

    private fun setSelectedColor(view: HorizontalPicker, degree: Int) {
        view.setSelectedColor(degree)
    }

}