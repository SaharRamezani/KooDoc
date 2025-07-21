package com.example.kidzi.util

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.widget.DatePicker
import com.example.kidzi.R
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import java.util.*
import saman.zamani.persiandate.PersianDate

fun toPersianNumber(input: Int): String {
    val persianDigits = arrayOf('۰','۱','۲','۳','۴','۵','۶','۷','۸','۹')
    return input.toString().map { if (it.isDigit()) persianDigits[it.digitToInt()] else it }.joinToString("")
}

fun parsePersianDateToGregorianMillis(persianDate: String): Long {
    return try {
        val parts = persianDate.split("/")
        val (year, month, day) = parts.map { it.toInt() }
        val persian = PersianDate()
        persian.setShYear(year)
        persian.setShMonth(month)
        persian.setShDay(day)
        persian.toDate().time // returns java.util.Date, get millis
    } catch (_: Exception) {
        0L
    }
}

fun shamsiToGregorian(year: Int, month: Int, day: Int): Date {
    val persianDate = PersianDate()
    persianDate.setShYear(year)
    persianDate.setShMonth(month)
    persianDate.setShDay(day)
    return persianDate.toDate() // java.util.Date (Gregorian)
}

fun gregorianToShamsi(date: Date): Triple<Int, Int, Int> {
    val persianDate = PersianDate(date)
    return Triple(persianDate.shYear, persianDate.shMonth, persianDate.shDay)
}

fun getAgeInMonths(dateStr: String, locale: Locale = Locale.getDefault()): Int {
    val gregorianMillis = when (locale.language) {
        "fa" -> parsePersianDateToGregorianMillis(dateStr)
        else -> parseGregorianDateToMillis(dateStr)
    }.takeIf { it != 0L } ?: return 0

    val dob = Calendar.getInstance().apply { timeInMillis = gregorianMillis }
    val now = Calendar.getInstance()

    var yearDiff = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
    var monthDiff = now.get(Calendar.MONTH) - dob.get(Calendar.MONTH)
    val dayDiff = now.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH)

    if (dayDiff < 0) {
        monthDiff -= 1
    }

    return yearDiff * 12 + monthDiff
}

fun parseGregorianDateToMillis(dateStr: String): Long {
    return try {
        val parts = dateStr.split("/")
        val (year, month, day) = parts.map { it.toInt() }
        Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    } catch (_: Exception) {
        0L
    }
}

fun showLocalizedDatePicker(
    context: Context,
    onDateSelected: (formattedDate: String) -> Unit
) {
    val locale = Locale.getDefault().language

    if (locale == "fa") {
        PersianDatePickerDialog(context)
            .setPositiveButtonString(context.getString(R.string.datepicker_positive))
            .setNegativeButton(context.getString(R.string.datepicker_negative))
            .setTodayButton(context.getString(R.string.datepicker_today))
            .setTodayButtonVisible(true)
            .setMinYear(1380)
            .setInitDate(1404, 1, 1)
            .setActionTextColor(Color.GRAY)
            .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
            .setShowInBottomSheet(true)
            .setPickerBackgroundColor(context.getColor(R.color.white))
            .setListener(object : PersianPickerListener {
                override fun onDateSelected(date: PersianPickerDate) {
                    val persianDate = toPersianNumber(date.persianYear) +
                            "/${toPersianNumber(date.persianMonth)}" +
                            "/${toPersianNumber(date.persianDay)}"
                    onDateSelected(persianDate)
                }

                override fun onDismissed() {}
            })
            .show()
    } else {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, { _: DatePicker, y: Int, m: Int, d: Int ->
            val formatted = String.format(Locale.ENGLISH, "%04d/%02d/%02d", y, m + 1, d)
            onDateSelected(formatted)
        }, year, month, day).show()
    }
}