package com.liu.bloodpressure.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import com.liu.bloodpressure.R
import com.liu.bloodpressure.util.DateKt
import com.liu.bloodpressure.util.toast
import com.liu.bloodpressure.util.xx

class DateAndTimePopupWindow(
    private val context: Context,
    private val currTime: Long,
    private val clickCancel: () -> Unit = {},
    private val clickSure: (Long) -> Unit
) : PopupWindow(context) {
    private var mCancel: Button
    private var mConfirm: Button

    private var mYear: HorizontalPicker
    private var mMonth: HorizontalPicker
    private var mDay: HorizontalPicker
    private var mHour: HorizontalPicker
    private var mSecond: HorizontalPicker

    private var mYearText: String = DateKt.getYear(currTime).toString()
    private var mMonthText: String = DateKt.getMonth(currTime).toString()
    private var mDayText: String = DateKt.getDay(currTime).toString()
    private var mHourText: String = DateKt.getHour(currTime).toString()
    private var mMinuteText: String = DateKt.getMinute(currTime).toString()

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
            DateKt.getMills(mYearText.toInt(), mMonthText.toInt(), mDayText.toInt(), mHourText.toInt(), mMinuteText.toInt()).let {
                if (it > System.currentTimeMillis()) {
                    "The time is incorrect,it cannot exceed te current time".toast(context)
                } else {
                    dismiss()
                    clickSure.invoke(it)
                }
            }

        }
        setData()
    }


    private fun setData() {
        mYearList = getMutList(mYearText.toInt() - 1, mYearText.toInt() + 1)
        mMonthList = getMutList(1, 12)
        mHourList = getMutList(0, 23)
        mSecondList = getMutList(0, 59)

        mYear.setData(mYearList, mYearList.indexOf(mYearText))
        mMonth.setData(mMonthList, mMonthList.indexOf((mMonthText.toInt() + 1).xx()))
        mHour.setData(mHourList, mHourList.indexOf(mHourText.toInt().xx()))
        mSecond.setData(mSecondList, mSecondList.indexOf(mMinuteText.toInt().xx()))
        setDay()
        mYear.onSelect = { value, _ ->
            mYearText = value
            setDay()
        }
        mMonth.onSelect = { value, _ ->
            mMonthText = "${value.toInt() - 1}"
            setDay()
        }
        mDay.onSelect = { value, _ ->
            mDayText = value
        }
        mHour.onSelect = { value, _ ->
            mHourText = value
        }
        mSecond.onSelect = { value, _ ->
            mMinuteText = value
        }
    }

    private fun setDay() {
        mDayList = getMutList(1, DateKt.getDay(mYearText.toInt(), mMonthText.toInt()))
        mDay.setData(mDayList, mDayList.indexOf(mDayText.toInt().xx()))
    }

    private fun getMutList(startNumber: Int, endNumber: Int): MutableList<String> {
        return mutableListOf<String>().apply {
            (startNumber..endNumber).forEach {
                add(it.xx())
            }
        }
    }

}