package com.serefakyuz.tindersample.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class StackRecyclerView: RecyclerView, DragListener {
    constructor(context: Context): super(context){
    }
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int): super(context, attrs, defStyle) {
    }

    init {

    }

    fun setSwipeListener(swipeListener: SwipeListener){
        if(layoutManager is StackLayoutManager){
            (layoutManager as StackLayoutManager).setDragListener(this, swipeListener)
        }
    }

    override fun onDrag(v: View, x: Float, y: Float) {
        (layoutManager as StackLayoutManager).run {
            changeChildViewScale(this@StackRecyclerView, getChildViewHolder(v), x, y)
            changeDragPercent(this@StackRecyclerView, x, y)
        }
    }

    override fun onDragEnded(v: View, x: Float, y: Float) {
        (layoutManager as StackLayoutManager).onDragEnded(this@StackRecyclerView, getChildViewHolder(v), x, y)
    }

}