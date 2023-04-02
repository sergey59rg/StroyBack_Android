package com.rbmstroy.rbmbonus.features.reset.presenter

import com.google.gson.Gson
import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.domain.UpdateInterceptor
import com.rbmstroy.rbmbonus.features.reset.ui.ResetView
import com.rbmstroy.rbmbonus.model.ErrorBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class ResetPresenter constructor(
    private val view: ResetView,
    private val updateInteractor: UpdateInterceptor
) : BasePresenter() {

    init {

    }

    fun update(phone: String, pass: String) {
        updateInteractor.update(phone, pass)
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
                    if (it.message == "status ok") {
                        view.auth()
                    } else {
                        view.showError(it.message)
                    }
                }
            ).addTo(disposable)
    }
}