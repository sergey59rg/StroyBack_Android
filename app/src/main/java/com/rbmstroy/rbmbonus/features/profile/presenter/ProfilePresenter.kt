package com.rbmstroy.rbmbonus.features.profile.presenter

import android.content.Context
import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.Constants
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.features.profile.ui.ProfileView
import com.rbmstroy.rbmbonus.model.Profile

class ProfilePresenter constructor(
    private val view: ProfileView,
    private val profileAdapter: ProfileAdapter,
    private val context: Context
) : BasePresenter()
{
    init {
        profileAdapter.viewSetClickListener = ::viewSetOnClick
        val data: ArrayList<Profile> = ArrayList()
        data.add(Profile(0, R.drawable.profile_fill, context.getString(R.string.change_data)))
        data.add(Profile(1, R.drawable.email_fill, context.getString(R.string.change_email)))
        data.add(Profile(2, R.drawable.lock_fill, context.getString(R.string.to_change_password)))
        data.add(Profile(3, R.drawable.sos_fill, "${context.getString(R.string.support)}\n${Constants.SUPPORT_PHONE}"))
        profileAdapter.add(data)
    }

    private fun viewSetOnClick(data: Profile) {
        view.getProfile(data)
    }
}