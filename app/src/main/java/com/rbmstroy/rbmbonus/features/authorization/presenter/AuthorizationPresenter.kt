package com.rbmstroy.rbmbonus.features.authorization.presenter

import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.domain.AuthorizationInterceptor
import com.rbmstroy.rbmbonus.features.authorization.ui.AuthorizationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import com.google.gson.Gson
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptor
import com.rbmstroy.rbmbonus.model.ErrorBody
import io.reactivex.rxkotlin.addTo
import retrofit2.HttpException

class AuthorizationPresenter constructor(
    private val view: AuthorizationView,
    private val authorizationInteractor: AuthorizationInterceptor,
    private val preferenceInteractor: PreferenceInterceptor
) : BasePresenter()
{
    init {

    }

    fun authorization(email: String, pass: String) {
        authorizationInteractor.auth(email, pass)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgress() }
            .subscribeBy(
                onError = { throwable ->
                    view.hideProgress()
                    throwable.message?.let {
                        try {
                            val error: HttpException = throwable as HttpException
                            if(error.response()?.errorBody() != null) {
                                val errorBody: String = error.response()!!.errorBody()!!.string()
                                val msg = Gson().fromJson(errorBody, ErrorBody::class.java)
                                view.showError(msg.error)
                            } else {
                                view.showError(it)
                            }
                        } catch (exception: Exception) {

                        }
                    }
                },
                onSuccess = {
                    view.hideProgress()
                    if (it.message == "auth ok") {
                        preferenceInteractor.setKeyStorage(it.token, "token")
                        preferenceInteractor.setKeyStorage(email, "email")
                        view.auth()
                    } else {
                        view.showError(it.message)
                    }
                }
            ).addTo(disposable)
    }
}