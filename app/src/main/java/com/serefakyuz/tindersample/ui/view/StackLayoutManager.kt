package com.serefakyuz.tindersample.ui.view

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.sqrt

class StackLayoutManager : RecyclerView.LayoutManager() {

    companion object {
        const val SWIPE_NONE = 0
        const val SWIPE_LEFT = 1
        const val SWIPE_RIGHT = 1 shl 1
        const val SWIPE_TOP = 1 shl 2
        const val SWIPE_BOTTOM = 1 shl 3

        const val PAGINATION_TRIGGER_THRESHOLD = 3
    }

    private var startXPosition: Float? = null
    private var startYPosition: Float? = null
    private var topPosition = 0

    private var dragListener: DragListener? = null
    private var eventListener: EventListener? = null

    private var swipeDirection = SWIPE_LEFT or SWIPE_RIGHT
    private var preloadCount = 3
    private var scaleGap = .1f
    private var swipeThreshold = .6f
    private var defaultElevation = 50f
    private var restoreScaleAnimationDuration = 200L
    private var autoDraggingAnimationDuration = 200L

    fun setDragListener(dragListener: DragListener, eventListener: EventListener) {
        this.dragListener = dragListener
        this.eventListener = eventListener
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private fun changeDefaultElevation(elevation: Float) {
        defaultElevation = defaultElevation.coerceAtLeast(elevation)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        detachAndScrapAttachedViews(recycler)
        if (itemCount < 1) return

        val startPosition = (itemCount - 1).coerceAtMost(topPosition + preloadCount - 1)

        for (position in startPosition downTo topPosition) {
            val isTopView = position == topPosition
            val drawPosition = position - topPosition
            val view = recycler.getViewForPosition(position)

            changeDefaultElevation(ViewCompat.getElevation(view))

            if (isTopView) view.postDelayed({ setTopViewOnTouchListener(view) }, 100)

            setElevation(
                view,
                if (isTopView) defaultElevation
                else defaultElevation - 1f * drawPosition
            )

            measureChildWithMargins(view, 0, 0)

            val params = view.layoutParams as RecyclerView.LayoutParams

            layoutDecorated(
                view,
                params.leftMargin + paddingLeft,
                params.topMargin + paddingTop,
                getDecoratedMeasuredWidth(view) + params.leftMargin + paddingLeft,
                getDecoratedMeasuredHeight(view) + params.topMargin + paddingTop
            )

            if (drawPosition > 0) {
                view.scaleX = validateScale(1 - scaleGap * drawPosition)
                view.scaleY = validateScale(1 - scaleGap * drawPosition)
            } else {
                view.scaleX = validateScale(1f)
                view.scaleY = validateScale(1f)
            }

            if (startXPosition == null || startYPosition == null) {
                initStartPosition(view.x, view.y)
            }

            addView(view)
        }
    }

    fun nextView() {
        topPosition++
        requestLayout()
    }

    fun previousView() {
        if (topPosition - 1 < 0) topPosition = 0
        else topPosition--
        requestLayout()
    }

    fun changeChildViewScale(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float
    ) {
        val swipeValue = sqrt(dX * dX + dY * dY.toDouble())
        val fraction = 1.0.coerceAtMost(swipeValue / getThreshold(viewHolder))
        val childCount = recyclerView.childCount

        for (i in 0 until childCount) {
            val child = recyclerView.getChildAt(i)
            val level: Int = childCount - i - 1
            if (level > 0) {
                val scale = 0f.coerceAtLeast(
                    1f.coerceAtMost((1 - scaleGap * level + fraction * scaleGap).toFloat())
                )
                child.scaleX = scale
                if (level < preloadCount - 1) {
                    child.scaleY = scale
                }
            }
        }
    }

    fun changeDragPercent(
        recyclerView: RecyclerView,
        dX: Float,
        dY: Float
    ) {
        val swipeWidthPercent = 1f.coerceAtMost(dX / recyclerView.measuredWidth)
        val swipeHeightPercent = 1f.coerceAtMost(dY / recyclerView.measuredHeight)

        if (isEnableDirection(judgeLeftOrRight(swipeWidthPercent))) {
            eventListener?.onChangeHorizontalDrag(
                judgeLeftOrRight(swipeWidthPercent),
                abs(swipeWidthPercent)
            )
        }
        if (isEnableDirection(judgeTopOrBottom(swipeHeightPercent))) {
            eventListener?.onChangeHorizontalDrag(
                judgeTopOrBottom(swipeHeightPercent),
                abs(swipeHeightPercent)
            )
        }
    }

    private fun setElevation(view: View, elevation: Float) {
        ViewCompat.setElevation(view, elevation)
    }

    private fun initStartPosition(x: Float, y: Float) {
        startXPosition = x
        startYPosition = y
    }

    private fun setTopViewOnTouchListener(topView: View) {
        var rawX = 0f
        var rawY = 0f
        topView.setOnTouchListener { v, event ->
            if (v.scaleX != 1f || v.scaleY != 1f) return@setOnTouchListener true

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    rawX = v.x - event.rawX
                    rawY = v.y - event.rawY
                }

                MotionEvent.ACTION_UP -> {
                    dragListener?.onDragEnded(v, rawX + event.rawX, rawY + event.rawY)
                    v.performClick()
                }

                MotionEvent.ACTION_MOVE -> {
                    dragListener?.onDrag(v, rawX + event.rawX, rawY + event.rawY)
                    transitionAnimation(v, rawX + event.rawX, rawY + event.rawY)
                }
            }
            true
        }
    }

    fun onDragEnded(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float
    ) {
        clearViewTouchListener(viewHolder.itemView)

        val swipeWidthPercent = 1f.coerceAtMost(dX / recyclerView.measuredWidth)
        val swipeHeightPercent = 1f.coerceAtMost(dY / recyclerView.measuredHeight)

        getCandidateDirection(
            swipeWidthPercent,
            swipeHeightPercent
        ).firstOrNull { isEnableDirection(it) }
            ?.let {
                onSwiped(
                    viewHolder.itemView,
                    getSecondView(recyclerView),
                    it
                )
            }
            ?: kotlin.run {
                transitionAnimation(
                    viewHolder.itemView,
                    startXPosition ?: 0f,
                    startYPosition ?: 0f,
                    restoreScaleAnimationDuration,
                    {
                        dragListener?.onDrag(it, it.x, it.y)
                    },
                    {
                        postOnAnimation { setTopViewOnTouchListener(viewHolder.itemView) }
                    })
            }
    }

    private fun isSwiped(swipedPercent: Float): Boolean {
        return swipedPercent > swipeThreshold
    }

    private fun getThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return viewHolder.itemView.width * 0.9f
    }

    private fun judgeTopOrBottom(percent: Float): Int =
        if (percent > 0) SWIPE_BOTTOM
        else SWIPE_TOP

    private fun judgeLeftOrRight(percent: Float): Int =
        if (percent > 0) SWIPE_RIGHT
        else SWIPE_LEFT

    private fun onSwiped(
        swipedView: View,
        secondView: View?,
        direction: Int
    ) {
        fun swipedAnimation(x: Float, y: Float) {
            transitionAnimation(swipedView, x, y, autoDraggingAnimationDuration) {
                resetViewPosition(swipedView)
                val currentPosition = topPosition
                topPosition++
                if(topPosition == itemCount - PAGINATION_TRIGGER_THRESHOLD){
                    eventListener?.onLoadMoreItems()
                }
                requestLayout()
                eventListener?.onSwiped(currentPosition, direction)
            }
        }

        if (secondView != null) {
            restoreScaleAnimation(secondView, autoDraggingAnimationDuration)
        }

        val value = 3f

        when (direction) {
            SWIPE_TOP -> {
                swipedAnimation(swipedView.x * value, swipedView.y - swipedView.height)
            }

            SWIPE_BOTTOM -> {
                swipedAnimation(swipedView.x * value, swipedView.y + swipedView.height)
            }

            SWIPE_LEFT -> {
                swipedAnimation(swipedView.x - swipedView.width, swipedView.y * value)
            }

            SWIPE_RIGHT -> {
                swipedAnimation(swipedView.x + swipedView.width, swipedView.y * value)
            }
        }
    }

    private fun validateScale(value: Float): Float {
        return 0f.coerceAtLeast(1f.coerceAtMost(value))
    }

    private fun transitionAnimation(
        view: View,
        x: Float,
        y: Float,
        duration: Long = 0,
        updateListener: ((View) -> Unit)? = null,
        endAction: (() -> Unit)? = null
    ) {
        val animation = ViewCompat.animate(view)
            .x(x)
            .y(y)
            .setUpdateListener(updateListener)
            .setDuration(duration)
        endAction?.let {
            animation.withEndAction(it)
        }
        animation.start()
    }

    private fun restoreScaleAnimation(view: View, duration: Long = 0) {
        view.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(duration)
            .start()
    }

    private fun resetViewPosition(view: View) {
        view.x = startXPosition ?: 0f
        view.y = startYPosition ?: 0f
    }

    private fun clearViewTouchListener(view: View) {
        view.setOnTouchListener(null)
    }

    private fun getSecondView(recyclerView: RecyclerView): View? {
        if (recyclerView.childCount < 2) return null
        return recyclerView.getChildAt(childCount - 2)
    }

    private fun getTopView(recyclerView: RecyclerView): View? {
        if (recyclerView.childCount == 0) return null
        return recyclerView.getChildAt(childCount - 1)
    }

    private fun getCandidateDirection(
        widthPercent: Float,
        heightPercent: Float
    ): List<Int> {
        val candidates = arrayListOf<Pair<Float, Int>>()
        if (isSwiped(abs(widthPercent))) {
            candidates.add(widthPercent to judgeLeftOrRight(widthPercent))
        }
        if (isSwiped(abs(heightPercent))) {
            candidates.add(heightPercent to judgeTopOrBottom(heightPercent))
        }
        return candidates
            .sortedByDescending { abs(it.first) }
            .map { it.second }
    }

    private fun isEnableDirection(direction: Int) =
        (swipeDirection and direction) == direction
}