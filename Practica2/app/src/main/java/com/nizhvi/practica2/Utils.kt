package com.nizhvi.practica2

object Utils {
    fun isALeapYear(year: Int): Boolean {
        return year % 400 == 0 || year % 4 == 0 && year % 100 != 0
    }
}