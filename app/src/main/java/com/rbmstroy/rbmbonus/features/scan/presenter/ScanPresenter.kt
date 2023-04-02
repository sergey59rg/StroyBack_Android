package com.rbmstroy.rbmbonus.features.scan.presenter

import android.content.Context
import com.google.gson.Gson
import com.rbmstroy.rbmbonus.BasePresenter
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.domain.ScanInterceptor
import com.rbmstroy.rbmbonus.features.scan.ui.ScanView
import com.rbmstroy.rbmbonus.model.ErrorBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class ScanPresenter constructor(
    private val view: ScanView,
    private val scanInteractor: ScanInterceptor,
    private val context: Context
) : BasePresenter()
{
    var isLocation = false

    init {

    }

    fun scan(token: String, geo: String, qr_code: String) {
        println("${token} ${geo} ${qr_code}")
        scanInteractor.scan(token, geo, qr_code)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgress() }
            .subscribeBy(
                onError = { throwable ->
                    view.hideProgress()
                    throwable.message?.let {
                        try {
                            val error: HttpException = throwable as HttpException
                            if (error.response()?.errorBody() != null) {
                                val errorBody: String =
                                    error.response()!!.errorBody()!!.string()
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
                    if (it.message == "scan ok") {
                        view.success(it)
                    } else if (it.message == "scan err") {
                        view.error(context.getString(R.string.qrcode_registered), context.getString(R.string.remind_scanning_qr))
                    } else if (it.message == "scan errgeo") {
                        if (it.errtext == null) {
                            view.error(context.getString(R.string.error_geolocation), context.getString(R.string.scanning_qr_code), "error")
                        } else {
                            view.error(context.getString(R.string.error_geolocation), it.errtext, "error")
                        }
                    }
                }
            ).addTo(disposable)
    }
}