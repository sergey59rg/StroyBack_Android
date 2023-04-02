package com.rbmstroy.rbmbonus

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter {

    protected val disposable: CompositeDisposable = CompositeDisposable()

    fun dispose() {
        disposable.dispose()
    }
}