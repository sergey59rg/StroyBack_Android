package com.rbmstroy.rbmbonus.utils

import java.util.regex.Pattern

class VersionUtils {

    companion object {

        fun compareVersion(v1: String, v2: String): String? {
            if (v1 == null || v1.length < 1 || v2 == null || v2.length < 1) return null
            val regEx = "[^0-9]"
            val p = Pattern.compile(regEx)
            var s1: String = p.matcher(v1).replaceAll("").trim()
            var s2: String = p.matcher(v2).replaceAll("").trim()

            val cha: Int = s1.length - s2.length
            var buffer = StringBuffer()
            var i = 0
            while (i < Math.abs(cha)) {
                buffer.append("0")
                ++i
            }

            if (cha > 0) {
                buffer.insert(0,s2)
                s2 = buffer.toString()
            } else if (cha < 0) {
                buffer.insert(0,s1)
                s1 = buffer.toString()
            }

            val s1Int = s1.toInt()
            val s2Int = s2.toInt()

            if (s1Int > s2Int) return v1
            else return v2
        }

    }
}