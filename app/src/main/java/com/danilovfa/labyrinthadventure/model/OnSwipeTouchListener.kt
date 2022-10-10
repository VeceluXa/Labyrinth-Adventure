package com.danilovfa.labyrinthadventure.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlin.math.abs

class OnSwipeTouchListener(context: Context) : View.OnTouchListener {
    private var gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, GestureListener(context))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private class GestureListener(context: Context): GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
        private val viewContext: Context
        init {
            viewContext = context
        }
//        override fun onDown(e: MotionEvent?): Boolean {
//            return true
//        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            val x1: Float = e1?.x ?: 0F
            val x2: Float = e2?.x ?: 0F
            val y1: Float = e1?.y ?: 0F
            val y2: Float = e2?.y ?: 0F

            Log.println(Log.INFO, "onFling", "onFling")

            val diffX: Float = x2 - x1
            val diffY: Float = y2 - y1
            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0)
                        onSwipeRight()
                    else
                        onSwipeLeft()
                    result = true
                }

            } else if (abs(diffY) > abs(diffX)) {
                if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0)
                        onSwipeDown()
                    else
                        onSwipeUp()
                    result = true
                }
            }

            return result
        }

        fun onSwipeLeft(): Boolean {
            Toast.makeText(viewContext, "Swipe Left", Toast.LENGTH_SHORT).show()
            return true
        }

        fun onSwipeRight(): Boolean {
            Toast.makeText(viewContext, "Swipe Right", Toast.LENGTH_SHORT).show()
            return true
        }

        fun onSwipeUp(): Boolean {
            Toast.makeText(viewContext, "Swipe Up", Toast.LENGTH_SHORT).show()
            return true
        }

        fun onSwipeDown(): Boolean {
            Toast.makeText(viewContext, "Swipe Down", Toast.LENGTH_SHORT).show()
            return true
        }
    }
}

