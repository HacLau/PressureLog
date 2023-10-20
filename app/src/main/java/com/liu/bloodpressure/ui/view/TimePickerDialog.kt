package com.liu.bloodpressure.ui.view


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TimePicker
import com.liu.bloodpressure.R


class TimePickerDialog(private val mContext: Context,
                       private val positive:()->Unit = {},
                       private val negative:()->Unit = {}) {
    private var mAlertDialog: AlertDialog.Builder? = null
    var hour = 0
        private set
    var minute = 0
        private set
    private var mTimePicker: TimePicker? = null
    private var mDatePicker: DatePicker? = null
    private var mTag = 0
    var year = 0
        private set
    var day = 0
        private set
    private var mMonth = 0

    private fun initDatePicker(): View {
        val inflate: View = LayoutInflater.from(mContext).inflate(
            R.layout.dateandtimepicker_layout, null
        )
        mTimePicker = inflate
            .findViewById<View>(R.id.time_picker) as TimePicker
        mTimePicker?.visibility = View.GONE
        mDatePicker = inflate
            .findViewById<View>(R.id.date_picker) as DatePicker
        mTimePicker!!.setIs24HourView(true)
        resizePikcer(mTimePicker)
        resizePikcer(mDatePicker)
        return inflate
    }
    private fun initTimePicker(): View {
        val inflate: View = LayoutInflater.from(mContext).inflate(
            R.layout.dateandtimepicker_layout, null
        )
        mTimePicker = inflate
            .findViewById<View>(R.id.time_picker) as TimePicker
        mDatePicker = inflate
            .findViewById<View>(R.id.date_picker) as DatePicker
        mDatePicker?.visibility = View.GONE
        mTimePicker!!.setIs24HourView(true)
        resizePikcer(mTimePicker)
        resizePikcer(mDatePicker)
        return inflate
    }

    private fun initDateAndTimePicker(): View {
        val inflate: View = LayoutInflater.from(mContext).inflate(
            R.layout.dateandtimepicker_layout, null
        )
        mTimePicker = inflate
            .findViewById<View>(R.id.time_picker) as TimePicker
        mDatePicker = inflate
            .findViewById<View>(R.id.date_picker) as DatePicker
        mTimePicker!!.setIs24HourView(true)
        resizePikcer(mTimePicker)
        resizePikcer(mDatePicker)
        return inflate
    }

    /**
     * 创建dialog
     *
     * @param view
     */
    private fun initDialog(view: View) {
        mAlertDialog?.setPositiveButton("确定",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    // TODO Auto-generated method stub
                    dialog.dismiss()
                    if (mTag == 0) {
                        timePickerValue
                    } else if (mTag == 1) {
                        datePickerValue
                    } else if (mTag == 2) {
                        datePickerValue
                        timePickerValue
                    }
                    positive.invoke()
                }
            })
        mAlertDialog?.setNegativeButton("取消",
            DialogInterface.OnClickListener { dialog, _ -> // TODO Auto-generated method stub
                negative.invoke()
                dialog.dismiss()
            })
        mAlertDialog?.setView(view)
    }

    /**
     * 显示时间选择器
     */
    fun showTimePickerDialog() {
        mTag = 0
        val view = initTimePicker()
        mAlertDialog = AlertDialog.Builder(mContext)
        mAlertDialog?.setTitle("选择时间")
        initDialog(view)
        mAlertDialog?.show()
    }

    /**
     * 显示日期选择器
     */
    fun showDatePickerDialog() {
        mTag = 1
        val view = initDatePicker()
        mAlertDialog = AlertDialog.Builder(mContext)
        mAlertDialog?.setTitle("选择时间")
        initDialog(view)
        mAlertDialog?.show()
    }

    /**
     * 显示日期选择器
     */
    fun showDateAndTimePickerDialog() {
        mTag = 2
        val view = initDateAndTimePicker()
        mAlertDialog = AlertDialog.Builder(mContext)
        mAlertDialog?.setTitle("选择时间")
        initDialog(view)
        mAlertDialog?.show()
    }

    /*
    * 调整numberpicker大小
    */
    private fun resizeNumberPicker(np: NumberPicker) {
        val params = LinearLayout.LayoutParams(
            120,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(10, 0, 10, 0)
        np.layoutParams = params
    }

    /**
     * 调整FrameLayout大小
     *
     * @param tp
     */
    private fun resizePikcer(tp: FrameLayout?) {
        val npList = findNumberPicker(tp)
        for (np in npList) {
            resizeNumberPicker(np)
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     *
     * @param viewGroup
     * @return
     */
    private fun findNumberPicker(viewGroup: ViewGroup?): List<NumberPicker> {
        val npList: MutableList<NumberPicker> = ArrayList()
        var child: View? = null
        if (null != viewGroup) {
            for (i in 0 until viewGroup.childCount) {
                child = viewGroup.getChildAt(i)
                if (child is NumberPicker) {
                    npList.add(child)
                } else if (child is LinearLayout) {
                    val result = findNumberPicker(child as ViewGroup?)
                    if (result.size > 0) {
                        return result
                    }
                }
            }
        }
        return npList
    }

    val month: Int
        get() =//返回的时间是0-11
            mMonth + 1
    private val datePickerValue: Unit
        /**
         * 获取日期选择的值
         */
        private get() {
            year = mDatePicker!!.year
            mMonth = mDatePicker!!.month
            day = mDatePicker!!.dayOfMonth
        }
    private val timePickerValue: Unit
        /**
         * 获取时间选择的值
         */
        private get() {
            // api23这两个方法过时
            hour = mTimePicker!!.currentHour // timePicker.getHour();
            minute = mTimePicker!!.currentMinute // timePicker.getMinute();
        }

    interface TimePickerDialogInterface {
        fun positiveListener()
        fun negativeListener()
    }
}

