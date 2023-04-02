package com.rbmstroy.rbmbonus.domain

import com.rbmstroy.rbmbonus.data.prefs.Preference

interface PreferenceInterceptor {

    fun getKeyStorage(name: String): Any?

    fun setKeyStorage(value: Any, name: String)

    fun removeKeyStorage(name: String)

    fun logout()

}

class PreferenceInterceptorImpl(private val preference: Preference): PreferenceInterceptor {

    override fun getKeyStorage(name: String): Any? = preference.getKeyStorage(name)

    override fun setKeyStorage(value: Any, name: String) {
        preference.setKeyStorage(value, name)
    }

    override fun removeKeyStorage(name: String) = preference.removeKeyStorage(name)

    override fun logout() = preference.logout()

}