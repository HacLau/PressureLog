package com.liu.bloodpressure.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import com.liu.bloodpressure.R
import com.liu.bloodpressure.util.DateKt

class DateAndTimePopupWindow(
    private val context: Context,
    private val currTime: String = "",
    private val clickCancel: () -> Unit = {},
    private val clickSure: (String) -> Unit
) : PopupWindow(context) {
    private var mCancel: Button
    private var mConfirm: Button

    private var mYear: HorizontalPicker
    private var mMonth: HorizontalPicker
    private var mDay: HorizontalPicker
    private var mHour: HorizontalPicker
    private var mSecond: HorizontalPicker

    private var mYearText: String = DateKt.getYear().toString()
    private var mMonthText: String = DateKt.getMonth().toString()
    private var mDayText: String = DateKt.getDay().toString()
    private var mHourText: String = DateKt.getHour().toString()
    private var mSecondText: String = DateKt.getSecond().toString()

    private lateinit var mYearList: MutableList<String>
    private lateinit var mMonthList: MutableList<String>
    private lateinit var mDayList: MutableList<String>
    private lateinit var mHourList: MutableList<String>
    private lateinit var mSecondList: MutableList<String>

    init {
        height = ViewGroup.LayoutParams.MATCH_PARENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        isFocusable = true
        background.alpha = 160
        animationStyle = R.style.popwin_anim_style
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_date, null, false)
        mCancel = contentView.findViewById(R.id.dialog_cancel)
        mConfirm = contentView.findViewById(R.id.dialog_confirm)
        mYear = contentView.findViewById(R.id.hp_year)
        mMonth = contentView.findViewById(R.id.hp_month)
        mDay = contentView.findViewById(R.id.hp_day)
        mHour = contentView.findViewById(R.id.hp_hour)
        mSecond = contentView.findViewById(R.id.hp_second)

        mCancel.setOnClickListener {
            dismiss()
            clickCancel.invoke()
        }
        mConfirm.setOnClickListener {
            dismiss()
            clickSure.invoke("$mYearText-$mMonthText-$mDayText $mHourText:$mSecondText")
        }
        setDate()
        setData()
    }

    private fun setDate() {
        if (currTime.isBlank()) {
            mYearText = DateKt.getYear().toString()
            mMonthText = DateKt.getMonth().toString()
            mDayText = DateKt.getDay(mYearText.toInt(), mMonthText.toInt()).toString()
            mHourText = DateKt.getHour().toString()
            mSecondText = DateKt.getSecond().toString()
        } else {
            currTime.replace("-", " ").replace(":", " ").split(" ").let {
                mYearText = it[0]
                mMonthText = it[1]
                mDayText = it[2]
                mHourText = it[3]
                mSecondText = it[4]

            }
        }
    }

    private fun setData() {
        mYearList = getMutList(1970, mYearText.toInt() + 10)
        mMonthList = getMutList(1, 12)
        mHourList = getMutList(0, 23)
        mSecondList = getMutList(0, 59)

        mYear.setData(mYearList, mYearList.indexOf(mYearText))
        mMonth.setData(mMonthList, mMonthList.indexOf(mMonthText))
        mHour.setData(mHourList, mHourList.indexOf(mHourText))
        mSecond.setData(mSecondList, mSecondList.indexOf(mSecondText))
        setDay()
        mYear.onSelect = { value, _ ->
            mYearText = value
            setDay()
        }
        mMonth.onSelect = { value, _ ->
            mMonthText = value
            setDay()
        }
        mDay.onSelect = { value, _ ->
            mDayText = value
        }
        mHour.onSelect = { value, _ ->
            mHourText = value
        }
        mSecond.onSelect = { value, _ ->
            mSecondText = value
        }
    }

    private fun setDay() {
        mDayList = getMutList(1, DateKt.getDay(mYearText.toInt(), mMonthText.toInt()))
        mDay.setData(mDayList, mDayList.indexOf(mDayText))
    }

    private fun getMutList(startNumber: Int, endNumber: Int): MutableList<String> {
        return mutableListOf<String>().apply {
            (startNumber..endNumber).forEach {
                add(it.toString())
            }
        }
    }

}