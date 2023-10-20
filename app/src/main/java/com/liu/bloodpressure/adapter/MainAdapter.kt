package com.liu.bloodpressure.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class MainAdapter(
    private val data: MutableList<View>
) : PagerAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(data[position])
        return data[position]
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}