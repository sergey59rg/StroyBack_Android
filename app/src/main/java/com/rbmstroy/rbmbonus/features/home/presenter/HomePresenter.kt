package com.rbmstroy.rbmbonus.features.home.presenter

import com.google.gson.Gson
import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.domain.PreferenceInterceptor
import com.rbmstroy.rbmbonus.domain.UserInterceptor
import com.rbmstroy.rbmbonus.domain.VersionInterceptor
import com.rbmstroy.rbmbonus.features.home.ui.HomeView
import com.rbmstroy.rbmbonus.model.ErrorBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class HomePresenter constructor(
    private val view: HomeView,
    private val userInteractor: UserInterceptor,
    private val versionInteractor: VersionInterceptor,
    private val preferenceInteractor: PreferenceInterceptor
) : BasePresenter()
{
    init {

    }

    fun user(token: String) {
        userInteractor.user(token)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  }
            .subscribeBy(
                onError = { throwable ->
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
                    if (it.message == "ok") {
                        preferenceInteractor.setKeyStorage(it.username, "username")
                        preferenceInteractor.setKeyStorage(it.balance, "balance")
                        preferenceInteractor.setKeyStorage(it.id, "id")
                        view.update(it)
                    } else if (it.message == "token expired!") {
                        view.exit()
                    } else {
                        view.showError(it.message)
                    }
                }
            ).addTo(disposable)
    }

    fun version() {
        versionInteractor.version()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  }
            .subscribeBy(
                onError = { throwable ->
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
                    view.version(it)
                }
            ).addTo(disposable)
    }
}