package com.rbmstroy.rbmbonus.features.confirmations.presenter

import com.google.gson.Gson
import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.domain.ConfirmationsInterceptor
import com.rbmstroy.rbmbonus.features.confirmations.ui.ConfirmationsView
import com.rbmstroy.rbmbonus.model.Confirmations
import com.rbmstroy.rbmbonus.model.ErrorBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class ConfirmationsPresenter constructor(
    private val view: ConfirmationsView,
    private val confirmationsInteractor: ConfirmationsInterceptor,
    private val confirmationsAdapter: ConfirmationsAdapter,
) : BasePresenter()
{
    init {
        confirmationsAdapter.viewSetClickListener = ::viewSetOnClick
    }

    fun confirmations(token: String) {
        confirmationsInteractor.confirmations(token)
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
                    confirmationsAdapter.add(it.items)
                }
            ).addTo(disposable)
    }

    private fun viewSetOnClick(data: Confirmations) {
        view.getBrend(data)
    }
}