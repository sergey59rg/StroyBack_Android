package com.rbmstroy.rbmbonus.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardUtils {

    companion object {

        fun hideKeyboard(activity: Activity, view: View) {
            val inputMethodManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun showKeyboard(activity: Activity) {
            val inputMethodManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        fun addKeyboardVisibilityListener(rootLayout: View, onKeyboardVisibiltyListener: OnKeyboardVisibiltyListener) {

        }

        interface OnKeyboardVisibiltyListener {
            fun onVisibilityChange(isVisible: Boolean)
        }

    }
}