package com.nzv.exam

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
object Utils {
    const val TAG_APP = "myExplicitIntent"
    const val EXTRA_APP = "myApp"
    const val EXTRA_TEMA = "myTema"
    const val EXTRA_HORAS = "myHoras"
    const val EXTRA_MODALITY = "myModality"


    fun closeKeyboard(view: View, activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showSnackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}