package com.liu.bloodpressure.ui.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.liu.bloodpressure.util.dp2px

class Decoration(private val itemSpace:Int):ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        itemSpace.dp2px(parent.context).let {
            outRect.left = it
            outRect.right = it
            outRect.top = it
            outRect.bottom = it
        }

    }
}