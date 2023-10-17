package com.nizhvi.practica1

object Utils {
    fun IsALeapYear(year: Int): Boolean {
        return year % 400 == 0 || year % 4 == 0 && year % 100 != 0
    }
}