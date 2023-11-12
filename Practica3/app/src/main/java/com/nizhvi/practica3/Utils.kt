package com.nizhvi.practica3

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.io.OutputStreamWriter

object Utils {
    const val TAG_APP = "myExplicitIntent"
    const val EXTRA_NAME = "myName"
    const val EXTRA_MODALITY = "myModality"
    const val EXTRA_DAY = "myDay"
    const val EXTRA_MONTH = "myMonth"
    const val EXTRA_YEAR = "myYear"
    const val EXTRA_CYCLE = "myCycle"
    const val EXTRA_GROUP = "myGroup"
    const val EXTRA_CLASSROOM = "myClassroom"
    //Comprueba si un año es bisiesto o no
    fun isALeapYear(year: Int): Boolean {
        return year % 400 == 0 || year % 4 == 0 && year % 100 != 0
    }
    //Devuelve el grupo al que pertenece un estudiante según el ciclo y la modalidad
    fun getGroupStudent(cycle: Cycle, modality: Modality): Group {
        return when (cycle) {
            Cycle.ASIR ->
                return if (modality == Modality.PRESENCIAL)
                    Group.A
                else
                    Group.B
            Cycle.DAM ->
                return if (modality == Modality.PRESENCIAL)
                    Group.C
                else
                    Group.D
            Cycle.DAW ->
                return if (modality == Modality.PRESENCIAL)
                    Group.E
                else
                    Group.F
        }
    }

    //Devuelve el string que te dice el aula según al grupo que pertence
    fun getClassRoom(group: Group): String {
        return when (group) {
            Group.A -> "201"
            Group.B -> "202"
            Group.C -> "203"
            Group.D -> "204"
            Group.E -> "205"
            Group.F -> "206"
        }
    }
    //Devuelve la modalidad según el boolean isPresencial
    fun getModality(isPresencial: Boolean): String {
        return if (isPresencial)
            "(Presencial)"
        else
            "(Semipresencial)"
    }

    //Devuelve un string con el nombre del mes según su número
    fun getMonth(position: Int): String {
        return when (position) {
            1 -> "Enero"
            2 -> "Febrero"
            3 -> "Marzo"
            4 -> "Abril"
            5 -> "Mayo"
            6 -> "Junio"
            7 -> "Julio"
            8 -> "Agosto"
            9 -> "Septiembre"
            10 -> "Octubre"
            11 -> "Noviembre"
            12 -> "Diciembre"
            else -> "Sin asignar"
        }
    }
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