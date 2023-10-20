package com.liu.bloodpressure.ui.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.liu.bloodpressure.R

class DateAndTimeDialog(
    private val context: Context,
    private val clickCancel: () -> Unit,
    private val clickSure: (String) -> Unit
) : Dialog(context) {
    private lateinit var mCancel:Button
    private lateinit var mConfirm:Button

    private lateinit var mYear:HorizontalPicker
    private lateinit var mMonth:HorizontalPicker
    private lateinit var mDay:HorizontalPicker
    private lateinit var mHour:HorizontalPicker
    private lateinit var mSecond:HorizontalPicker

    private var mDateTime:Long = 0L
    private lateinit var mYearText:String
    private lateinit var mMonthText:String
    private lateinit var mDayText:String
    private lateinit var mHourText:String
    private lateinit var mSecondText:String

    private lateinit var mYearList: MutableList<String>
    private lateinit var mMonthList: MutableList<String>
    private lateinit var mDayList: MutableList<String>
    private lateinit var mHourList: MutableList<String>
    private lateinit var mSecondList: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_dialog_date)
        mCancel = findViewById(R.id.dialog_cancel)
        mConfirm = findViewById(R.id.dialog_confirm)

        mYear = findViewById(R.id.hp_year)
        mMonth = findViewById(R.id.hp_month)
        mDay = findViewById(R.id.hp_day)
        mHour= findViewById(R.id.hp_hour)
        mSecond= findViewById(R.id.hp_second)

    }
}