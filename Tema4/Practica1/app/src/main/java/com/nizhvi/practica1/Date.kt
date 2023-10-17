package com.nizhvi.practica1
import java.util.Calendar
//Nicolas Zhan
class Date (var day:Int, var month:Int, var year:Int ) {

    fun getAge(): Int {
        var age: Int
        val hoy = Calendar.getInstance()
        var dia_hoy: Int = hoy.get(Calendar.DAY_OF_MONTH)
        var mes_hoy: Int = hoy.get(Calendar.MONTH) + 1
        var anyo_hoy: Int = hoy.get(Calendar.YEAR)

        age = anyo_hoy - year

        if (month >= mes_hoy && day > dia_hoy)
            age--

        return age
    }

    fun validateDay(day: Int): Boolean {
        var max = 31
        if (month != 0) max = GetMaxDayPerMonth()
        return (day in 1..max)
    }

    fun validateMonth(month: Int): Boolean {
        if (month < 1 || 12 < month)
            return false
        val max = GetMaxDayPerMonth()
        if (day != 0 && max < day)
            return false
        return true
    }

    fun validateYear(year: Int): Boolean {
        val leapYear = month == 2 && day > 28
        if (leapYear && !Utils.IsALeapYear(year))
            return false
        return true
    }

    private fun GetMaxDayPerMonth(): Int {
        var max = 31
        when (month) {
            4, 6, 9, 11 -> max = 30
            2 -> max = if (Utils.IsALeapYear(year)) 29 else 28
        }
        return max
    }
}


