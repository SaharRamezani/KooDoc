package com.example.kidzi.util

import android.content.Context
import android.graphics.Color
import com.example.kidzi.R
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener

fun showPersianDatePicker(
    context: Context,
    onDateSelected: (formattedDate: String) -> Unit
) {
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
        .setListener(object : PersianPickerListener {
            override fun onDateSelected(date: PersianPickerDate) {
                val formatted = "${date.persianYear}/${date.persianMonth}/${date.persianDay}"
                onDateSelected(formatted)
            }
            override fun onDismissed() {}
        })
        .show()
}
