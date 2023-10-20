package com.liu.bloodpressure.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.PopupWindow
import android.widget.TextView
import com.liu.bloodpressure.R

class HistoryPopupWindow(context: Context) : PopupWindow(context) {
    private lateinit var mRecent: TextView
    private lateinit var mWeek: TextView
    private lateinit var mMonth: TextView
    private lateinit var mYear: TextView
    private lateinit var onClick:()->Unit

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_history_popup, null, false)
        mRecent = view.findViewById(R.id.menu_recent)
        mMonth = view.findViewById(R.id.menu_month)
        mWeek = view.findViewById(R.id.menu_week)
        mYear = view.findViewById(R.id.menu_year)

    }
}