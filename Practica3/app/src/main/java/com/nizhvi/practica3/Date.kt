package com.nizhvi.practica3
import java.util.Calendar
//Nicolas Zhan
class Date (private var day:Int, private var month:Int, private var year:Int ) {
    //Devuelve la edad que tienes hoy según la fecha introducida
    fun getAge(): Int {
        var age: Int
        val hoy = Calendar.getInstance()
        val diaHoy: Int = hoy.get(Calendar.DAY_OF_MONTH)
        val mesHoy: Int = hoy.get(Calendar.MONTH) + 1
        val anyoHoy: Int = hoy.get(Calendar.YEAR)

        age = anyoHoy - year

        if (month > mesHoy || (month >= mesHoy && day > diaHoy))
            age--

        return age
    }

    private fun validateMonth(): Boolean {
        if (month < 1 || 12 < month)
            return false

        if (getMaxDayPerMonth() < day)
            return false
        return true
    }

    private fun validateYear(): Boolean {
        val leapYear = month == 2 && day > 28
        if (leapYear && !Utils.isALeapYear(year))
            return false
        return true
    }

    fun validateDate(): Boolean {
        if (day !in 1..31)
            return false
        if (!validateMonth())
            return false
        if (!validateYear())
            return false

        return true
    }

    private fun getMaxDayPerMonth(): Int {
        var max = 31
        when (month) {
            4, 6, 9, 11 -> max = 30
            2 -> max = if (Utils.isALeapYear(year)) 29 else 28
        }
        return max
    }
}


