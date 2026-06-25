package app.tildelauncher.listener

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

internal open class FabSwipeTouchListener(private val threshold: Float = 40f) : View.OnTouchListener {
    private var startX = 0f
    private var startY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.rawX
                startY = event.rawY
                return true
            }
            MotionEvent.ACTION_UP -> {
                val dx = event.rawX - startX
                val dy = event.rawY - startY
                if (abs(dx) < threshold && abs(dy) < threshold) {
                    view.performClick()
                    onClick()
                } else if (abs(dx) > abs(dy)) {
                    if (dx > 0) onSwipeRight() else onSwipeLeft()
                } else {
                    if (dy < 0) onSwipeUp() else onSwipeDown()
                }
                return true
            }
        }
        return false
    }

    open fun onSwipeUp() {}
    open fun onSwipeDown() {}
    open fun onSwipeLeft() {}
    open fun onSwipeRight() {}
    open fun onClick() {}
}
