package com.rbmstroy.rbmbonus.utils

import android.content.res.Resources

class MetricsUtils {

    companion object {

        fun getScreenWidth(): Int {
            return Resources.getSystem().getDisplayMetrics().widthPixels
        }

        fun getScreenHeight(): Int {
            return Resources.getSystem().getDisplayMetrics().heightPixels
        }
    }
}