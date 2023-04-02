package com.rbmstroy.rbmbonus.utils

import android.util.Patterns

class EmailValidator {
    companion object {
        @JvmStatic
        fun isValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}