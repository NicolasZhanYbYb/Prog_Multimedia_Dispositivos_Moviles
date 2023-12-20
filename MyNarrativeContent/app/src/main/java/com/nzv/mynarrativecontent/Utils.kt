package com.nzv.mynarrativecontent

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.QuerySnapshot

object Utils {
    const val TAG_APP = "myExplicitIntent"
    const val EXTRA_USER = "myUser"
    const val EXTRA_NAME = "myName"
    const val EXTRA_MODALITY = "myModality"
    const val EXTRA_DAY = "myDay"
    const val EXTRA_MONTH = "myMonth"
    const val EXTRA_YEAR = "myYear"
    const val EXTRA_CYCLE = "myCycle"
    const val EXTRA_GROUP = "myGroup"
    const val EXTRA_CLASSROOM = "myClassroom"
    //Cierra el teclado
     fun closeKeyboard(view: View, activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    //Muestra un mensaje en snackBar
    fun showSnackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
    //Int to Enum
    inline fun <reified T : Enum<T>> Int.toEnum(): T? {
        return enumValues<T>().firstOrNull { it.ordinal == this }
    }
    //Enum to Int
    inline fun <reified T : Enum<T>> T.toInt(): Int {
        return this.ordinal
    }
}