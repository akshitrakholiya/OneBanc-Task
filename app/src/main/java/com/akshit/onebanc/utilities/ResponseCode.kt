package com.akshit.onebanc.utilities

import android.widget.Toast
import com.akshit.onebanc.infra.CoreApplication

object ResponseCode {
    fun valid(code: Int?, message: String?): Boolean {
        when (code) {
            200 -> return true
            400 -> {
                message("The request body is incorrect or missing required fields.")
                return false
            }
            404 -> {
                message("The requested resource could not be found.")
                return false
            }
            500 -> {
                message("An unexpected error occurred on the server")
                return false
            }
            else -> {
                message(message)
                return false
            }
        }
    }

    private fun message(message: String?) {
        Toast.makeText(CoreApplication.appContext, message.toString(), Toast.LENGTH_LONG).show()
    }
}