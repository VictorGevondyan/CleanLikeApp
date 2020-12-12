package com.messapps.market_app.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtil {

    fun show(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideFrom(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = activity.currentFocus ?: View(activity)
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    fun hideFrom(context: Context, view: View?) {

        view
            ?: throw IllegalArgumentException("View is null. (KeyboardUtil - hideFrom(context: Context, view: View?))")

        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}