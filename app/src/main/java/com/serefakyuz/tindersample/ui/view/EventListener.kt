package com.serefakyuz.tindersample.ui.view

interface EventListener {

    fun onSwiped(position: Int, direction: Int)
    fun onChangeHorizontalDrag(direction: Int, percent: Float)
    fun onChangeVerticalDrag(direction: Int, percent: Float)
    fun onLoadMoreItems()
}