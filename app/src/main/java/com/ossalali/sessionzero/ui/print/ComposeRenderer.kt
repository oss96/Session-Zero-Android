package com.ossalali.sessionzero.ui.print

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView

object ComposeRenderer {

    fun renderToBitmap(
        activity: Activity,
        widthPx: Int,
        heightPx: Int,
        content: @Composable () -> Unit,
    ): Bitmap {
        val composeView = ComposeView(activity).apply {
            setContent { content() }
        }

        val root = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
        // Add off-screen so it doesn't flash
        composeView.alpha = 0f
        root.addView(
            composeView,
            ViewGroup.LayoutParams(widthPx, heightPx),
        )

        try {
            composeView.measure(
                View.MeasureSpec.makeMeasureSpec(widthPx, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(heightPx, View.MeasureSpec.EXACTLY),
            )
            composeView.layout(0, 0, widthPx, heightPx)

            val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            composeView.draw(canvas)
            return bitmap
        } finally {
            root.removeView(composeView)
        }
    }
}
