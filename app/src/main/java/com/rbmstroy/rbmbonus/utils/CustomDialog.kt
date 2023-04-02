package com.rbmstroy.rbmbonus.utils

import android.app.AlertDialog
import android.content.Context

class CustomDialog(context: Context) : AlertDialog.Builder(context) {

    lateinit var onResponse: (r : ResponseType) -> Unit

    enum class ResponseType {
        YES, NO, CANCEL
    }

    fun show(title: String, message: String, buttons: Array<String>, listener: (r : ResponseType) -> Unit) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        onResponse = listener

        var i = 0
        while( i in buttons.indices) {
            if (i==0) {
                builder.setPositiveButton(buttons[i]) { _, _ ->
                    onResponse(ResponseType.YES)
                }
            } else if (i==1) {
                builder.setNegativeButton(buttons[i]) { _, _ ->
                    onResponse(ResponseType.NO)
                }
            } else if (i==2) {
                builder.setNeutralButton(buttons[i]) { _, _ ->
                    onResponse(ResponseType.CANCEL)
                }
            }
            i++
        }

        val alertDialog: AlertDialog = builder.create()

        alertDialog.setCancelable(false)
        alertDialog.show()

    }

}