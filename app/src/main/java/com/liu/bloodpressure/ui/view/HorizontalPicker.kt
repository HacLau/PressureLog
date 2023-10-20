package com.liu.bloodpressure.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.liu.bloodpressure.R
import kotlin.math.abs

class HorizontalPicker : View {
    private var strings: List<String> = mutableListOf()
    private var seeSize = 0
    private var anInt = 0
    private var textPaint: TextPaint? = null
    private var firstVisible = false
    private var width = 0
    private var height = 0
    private var selectedPaint: TextPaint? = null
    private var index = 0
    private var downX = 0f
    private var anOffset = 0f
    private var selectedTextSize = 0f
    private var selectedColor = 0
    private var textSize = 0f
    private var textColor = 0
    private var rect: Rect? = null
    private var textWidth = 0
    private var textHeight = 0
    private var centerTextHeight = 0
    var onSelect: (String, Int) -> Unit = {_,_->}
    var onMove:(String,Int)-> Unit = {_,_->}

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
        initData()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(attrs)
        initData()
    }

    private fun initData() {
        strings = arrayListOf()
        seeSize = 5
        firstVisible = true
        rect = Rect()
        textWidth = 0
        textHeight = 0
        centerTextHeight = 0
        setWillNotDraw(false)
        this.isClickable = true
        initPaint()
    }


    private fun initPaint() {
        textPaint = TextPaint(1)
        textPaint!!.textSize = textSize
        textPaint!!.color = textColor
        selectedPaint = TextPaint(1)
        selectedPaint!!.color = selectedColor
        selectedPaint!!.textSize = selectedTextSize
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val tta = context!!.obtainStyledAttributes(attrs, R.styleable.HorizontalPicker)
        seeSize = tta.getInteger(R.styleable.HorizontalPicker_seesize, 5)
        selectedTextSize = tta.getFloat(R.styleable.HorizontalPicker_selectedTextSize, 60.0f)
        selectedColor = tta.getColor(R.styleable.HorizontalPicker_selectedTextColor, context!!.resources.getColor(R.color.degree_0))
        textSize = tta.getFloat(R.styleable.HorizontalPicker_normalTextSize, 50.0f)
        textColor = tta.getColor(R.styleable.HorizontalPicker_normalTextColor, context!!.resources.getColor(R.color.degree_1))
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
            }
            MotionEvent.ACTION_UP -> {
                anOffset = 0.0f
                this.invalidate()
                onSelect.invoke(getSelectedString(), index)
            }

            MotionEvent.ACTION_MOVE -> {
                val scrollX = event.x

                if (index != 0 && index != strings.size - 1) {
                    anOffset = scrollX - downX
                }
                if (index == 0 || index == strings.size - 1 || abs(anOffset) > abs(50 - strings.size.div(6.0f))) {
                    if (scrollX > downX) {
                        if (index > 0) {
                            anOffset = 0.0f
                            --index
                            downX = scrollX
                        }
                    } else if (index < strings.size - 1) {
                        anOffset = 0.0f
                        ++index
                        downX = scrollX
                    }
                }
                onMove.invoke(getSelectedString(), index)
                this.invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (firstVisible) {
            width = getWidth()
            height = getHeight()
            anInt = width / seeSize
            firstVisible = false
        }
        if (index >= 0 && index <= strings.size - 1) {
            selectedPaint!!.getTextBounds(strings[index], 0, strings[index].length, rect)
            val centerTextWidth = rect!!.width()
            centerTextHeight = rect!!.height()
            canvas.drawText(
                strings[index], (getWidth() / 2 - centerTextWidth / 2).toFloat() + anOffset, (getHeight() / 2 + centerTextHeight / 2).toFloat(),
                selectedPaint!!
            )
            for (i in strings.indices) {
                if (index > 0 && index < strings.size - 1) {
                    textPaint!!.getTextBounds(strings[index - 1], 0, strings[index - 1].length, rect)
                    val width1 = rect!!.width()
                    textPaint!!.getTextBounds(strings[index + 1], 0, strings[index + 1].length, rect)
                    val width2 = rect!!.width()
                    textWidth = (width1 + width2) / 2
                }
                if (i == 0) {
                    textPaint!!.getTextBounds(strings[0], 0, strings[0].length, rect)
                    textHeight = rect!!.height()
                }
                if (i != index) {
                    canvas.drawText(
                        strings[i],
                        ((i - index) * anInt + getWidth() / 2 - textWidth / 2).toFloat() + anOffset,
                        (getHeight() / 2 + textHeight / 2).toFloat(),
                        textPaint!!
                    )
                }
            }
        }
    }

    fun setSeeSize(seeSizes: Int) {
        if (seeSize > 0) {
            seeSize = seeSizes
            this.invalidate()
        }
    }

    fun setAnLeftOffset() {
        if (index < strings.size - 1) {
            ++index
            this.invalidate()
        }
    }

    fun setAnRightOffset() {
        if (index > 0) {
            --index
            this.invalidate()
        }
    }

    fun setData(strings: List<String>, index: Int = strings.size / 2) {
        this.strings = strings
        this.index = index
        this.invalidate()
    }

    fun setIndex(index: Int) {
        this.strings.let {
            if (index > it.size || index < 0) {
                this.index = it.size / 2
            } else
                this.index = index
            this.invalidate()
        }

    }

    fun setSeeValue(value: String) {
        this.strings.let {
            if (it.contains(value)) {
                this.index = it.indexOf(value)
                this.invalidate()
            }
        }
    }

    fun getIndexValue(value: String): Int {
        this.strings.let {
            if (it.contains(value)) {
                return it.indexOf(value)
            }
            return it.size - 1
        }
    }

    fun setSelectedColor(@ColorInt color:Int){
        selectedColor = color
    }

    private fun getSelectedString(): String {
        return if (strings.isNotEmpty()) strings[index] else ""
    }

}