package com.techstudio.extensions

import android.content.Context
import android.view.View

private const val CLICK_DELAY_MILLIS = 500L

fun View. setThrottleOnClickListener(callback: (view: View) -> Unit) {
    var lastClickTime = 0L

    this.setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis - lastClickTime > CLICK_DELAY_MILLIS) {
            lastClickTime = currentTimeMillis
            callback.invoke(it)
        }
    }
}

fun Context.dip(value: Int): Int = dipF(value).toInt()
fun Context.dipF(value: Int): Float = value * resources.displayMetrics.density

fun View.dip(value: Int): Int = context.dip(value)
