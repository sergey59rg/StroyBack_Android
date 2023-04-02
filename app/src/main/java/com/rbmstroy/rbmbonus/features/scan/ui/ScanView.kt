package com.rbmstroy.rbmbonus.features.scan.ui

import com.rbmstroy.rbmbonus.model.ScanResponse

interface ScanView {

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)

    fun success(data: ScanResponse)

    fun error(error: String, text: String, geo: String = "")
}