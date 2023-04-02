package com.rbmstroy.rbmbonus.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        fun dateToString(date: Date, pattern: String) : String {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val format = SimpleDateFormat(pattern)
            return format.format(calendar.time)
        }

        fun stringToDate(string: String, pattern: String) : Date {
            val format: DateFormat = SimpleDateFormat(pattern, Locale.getDefault())
            return format.parse(string)
        }

    }
}