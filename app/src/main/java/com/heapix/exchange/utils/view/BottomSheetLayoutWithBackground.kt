package com.heapix.exchange.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import kotlin.math.abs

class BottomSheetLayoutWithBackground @kotlin.jvm.JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var shadow: View = View(context)
    private var bottomSheetLayout: BottomSheetLayout

    init {
        shadow.alpha = 0f

        addView(shadow, 0)

        bottomSheetLayout = BottomSheetLayout(context, attrs)
        val layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
                gravity = Gravity.BOTTOM
            }

        bottomSheetLayout.layoutParams = layoutParams

        addView(bottomSheetLayout, 1)

        bottomSheetLayout.setOnProgressListener { progress ->
            val newAlpha = progress * 0.2f
            if (abs(newAlpha - shadow.alpha) > 0.005 || newAlpha == 0f || newAlpha == 0.3f) {
                shadow.alpha = newAlpha
            }

            if (progress == 0.0f || progress == 1.0f) {
                hideKeyboard(this)
            }
        }

        bottomSheetLayout.setOnClickListener {
            hide()
        }

    }

    fun toggle() = bottomSheetLayout.toggle()

    fun isExpanded(): Boolean = bottomSheetLayout.isExpanded()

    fun updateBottomSheet(updateBottomSheet: () -> Unit) = updateBottomSheet()

    private fun hide() = bottomSheetLayout.collapse()

    private fun hideKeyboard(view: View) {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        val view = getChildAt(2)
        removeViewAt(2)
        bottomSheetLayout.addView(view)
        bottomSheetLayout.reinit()
    }
}