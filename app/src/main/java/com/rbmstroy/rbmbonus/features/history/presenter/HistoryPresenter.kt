package com.rbmstroy.rbmbonus.features.history.presenter

import com.google.gson.Gson
import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.domain.HistoryInterceptor
import com.rbmstroy.rbmbonus.features.history.ui.HistoryView
import com.rbmstroy.rbmbonus.model.ErrorBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class HistoryPresenter constructor(
    private val view: HistoryView,
    private val historyInteractor: HistoryInterceptor,
    private val historyAdapter: HistoryAdapter,
) : BasePresenter()
{
    init {

    }

    fun history(token: String) {
        historyInteractor.history(token)
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
                    historyAdapter.add(it.items)
                }
            ).addTo(disposable)
    }
}