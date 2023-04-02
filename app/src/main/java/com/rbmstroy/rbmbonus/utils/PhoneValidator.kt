package com.rbmstroy.rbmbonus.utils

import android.telephony.PhoneNumberUtils

class PhoneValidator {
    companion object {
        fun isValid(phone: String?): Boolean {
            if (!phone.isNullOrEmpty()) {
                val formatted = PhoneNumberUtils.formatNumberToE164(phone.toString(), "RU")
                return PhoneNumberUtils.isGlobalPhoneNumber(formatted)
            }
            return false
        }
    }
}