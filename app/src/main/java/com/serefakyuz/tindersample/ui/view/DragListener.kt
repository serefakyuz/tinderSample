package com.serefakyuz.tindersample.ui.view

import android.view.View

interface DragListener {
    fun onDrag(v: View, x: Float, y: Float)
    fun onDragEnded(v: View, x: Float, y: Float)
}