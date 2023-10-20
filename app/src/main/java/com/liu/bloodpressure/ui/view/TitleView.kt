package com.liu.bloodpressure.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.liu.bloodpressure.R

class TitleView : ConstraintLayout {
    lateinit var title: TextView
    lateinit var leftText: TextView
    lateinit var leftImage: ImageView
    lateinit var rightText: TextView
    lateinit var rightImage: ImageView
    lateinit var rightButton: ConstraintLayout

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
        setStyle(context, attrs)
    }


    private fun setStyle(context: Context, attrs: AttributeSet?) {
        if (attrs != null){
            val array = context.obtainStyledAttributes(attrs,R.styleable.TitleView)
            array.getString(R.styleable.TitleView_leftText).let {
                leftText.text = it
            }
            array.getInt(R.styleable.TitleView_leftImageVisible,8).let {
                leftImage.visibility = it
            }
            array.getString(R.styleable.TitleView_middleText).let {
                title.text = it
            }
            array.getString(R.styleable.TitleView_rightText).let {
                rightText.text = it
            }
            array.getInt(R.styleable.TitleView_rightImageVisible,8).let {
                rightImage.visibility = it
            }
            array.getInt(R.styleable.TitleView_rightButtonVisible,8).let {
                rightButton.visibility = it
            }
            array.recycle()
        }
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_title, this, true)
        leftImage = view.findViewById(R.id.title_left_image)
        leftText = view.findViewById(R.id.title_left_text)
        title = view.findViewById(R.id.title_middle_text)
        rightImage = view.findViewById(R.id.title_right_image)
        rightText = view.findViewById(R.id.title_right_text)
        rightButton = view.findViewById(R.id.title_right_button)
    }
}