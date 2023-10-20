package com.liu.bloodpressure.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.liu.bloodpressure.R
import com.liu.bloodpressure.util.type.RecordType

class RecordPopupWindow(
    context: Context,
    var onClick: (String) -> Unit = {}
) : PopupWindow(context) {
    private var mRecent: TextView
    private var mWeek: TextView
    private var mMonth: TextView
    private var mLastMonth: TextView
    private var mAll: TextView


    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true
        background.alpha = 0
        animationStyle = R.style.popwin_anim_style
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_record_popup, null, false)
        mRecent = contentView.findViewById(R.id.menu_recent)
        mMonth = contentView.findViewById(R.id.menu_month)
        mWeek = contentView.findViewById(R.id.menu_week)
        mLastMonth = contentView.findViewById(R.id.menu_last_month)
        mAll = contentView.findViewById(R.id.menu_all)
        mRecent.setOnClickListener {
            dismiss()
            onClick.invoke(RecordType.RECENT)
        }
        mWeek.setOnClickListener {
            dismiss()
            onClick.invoke(RecordType.WEEK)
        }
        mMonth.setOnClickListener {
            dismiss()
            onClick.invoke(RecordType.MONTH)
        }
        mLastMonth.setOnClickListener {
            dismiss()
            onClick.invoke(RecordType.LASTMONTH)
        }
        mAll.setOnClickListener {
            dismiss()
            onClick.invoke(RecordType.ALL)
        }
    }
}