package com.rbmstroy.rbmbonus.data.prefs

import android.content.SharedPreferences
import com.rbmstroy.rbmbonus.App

class Preference {

    companion object {
        private const val PREFS_NAME = "com.rbmstroy.rbmbonus.prefs"
    }

    private var preferences: SharedPreferences

    init {
        preferences = App.instance.getSharedPreferences(PREFS_NAME, 0)
    }

    fun logout() {
        removeKeyStorage("token")
        removeKeyStorage("email")
        removeKeyStorage("username")
        removeKeyStorage("balance")
        removeKeyStorage("id")
    }

    fun getKeyStorage(name: String): Any? {
        val value = preferences.getString(name, null)
        return value
    }

    fun setKeyStorage(value: Any, name: String) {
        preferences.edit().apply {
            putString(name, value.toString())
            apply()
        }
    }

    fun removeKeyStorage(name: String) {
        preferences.edit().apply {
            remove(name)
            apply()
        }
    }
}