package com.rbmstroy.rbmbonus.features.buy.presenter

import android.content.Context
import android.widget.TextView
import com.google.gson.Gson
import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.domain.BuyInterceptor
import com.rbmstroy.rbmbonus.features.buy.ui.BuyView
import com.rbmstroy.rbmbonus.model.Buy
import com.rbmstroy.rbmbonus.model.ErrorBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class BuyPresenter constructor(
    private val view: BuyView,
    private val cardInteractor: BuyInterceptor,
    private val cardAdapter: BuyAdapter,
    private val context: Context,
    private val title: TextView
) : BasePresenter()
{
    init {
        cardAdapter.viewSetClickListener = ::viewSetOnClick
    }

    fun card() {
        cardInteractor.card()
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
                    cardAdapter.add(it.items)
                    title.setText(it.text)
                }
            ).addTo(disposable)
    }

    fun shop(name: String, price: String, token: String) {
        cardInteractor.shop(name, price, token)
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
                    if (it.message == "shop ok") {
                        view.success()
                    } else if (it.message == "shop err") {
                        view.error(context.getString(R.string.insufficient_funds), context.getString(
                            R.string.confirm_receipt_card))
                    }
                }
            ).addTo(disposable)
    }

    private fun viewSetOnClick(data: Buy) {
        view.getBrend(data)
    }
}