package com.rbmstroy.rbmbonus.features.accept.presenter

import com.google.gson.Gson
import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.domain.AcceptInterceptor
import com.rbmstroy.rbmbonus.features.accept.ui.AcceptView
import com.rbmstroy.rbmbonus.model.ErrorBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class AcceptPresenter constructor(
    private val view: AcceptView,
    private val acceptInteractor: AcceptInterceptor
) : BasePresenter()
{
    init {

    }

    fun accept(token: String, cardName: String, cardBalance: String) {
        acceptInteractor.accept(token, cardName, cardBalance)
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
                    if (it.message == "accept ok") {
                        view.back()
                    } else {
                        view.showError(it.message)
                    }
                }
            ).addTo(disposable)
    }
}