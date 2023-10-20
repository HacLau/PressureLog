package com.liu.bloodpressure.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.liu.bloodpressure.R
import com.liu.bloodpressure.model.StepEntity

class StepAdapter(
    private val context: Context,
    private val data: MutableList<StepEntity>
) : PagerAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_step, container, false)
        val title: TextView = view.findViewById(R.id.step_title)
        val context: TextView = view.findViewById(R.id.step_content)
        val bg: ImageView = view.findViewById(R.id.step_bg)
        title.text = data[position].title
        context.text = data[position].content
        bg.setBackgroundResource(data[position].imageUrl)
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}