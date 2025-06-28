package com.example.kidzi.util

import android.content.Context

object NumberFormatter {
    fun formatNumber(context: Context, number: String): String {
        val isEnglish = context.resources.configuration.locales.get(0).language == "en"
        return if (isEnglish) {
            number
        } else {
            toPersianDigits(number)
        }
    }

    private fun toPersianDigits(englishNumber: String): String {
        val persianDigits = arrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
        val builder = StringBuilder()
        for (ch in englishNumber) {
            if (ch in '0'..'9') {
                builder.append(persianDigits[ch - '0'])
            } else {
                builder.append(ch)
            }
        }
        return builder.toString()
    }

    fun formatNumber(context: Context, number: Int): String =
        formatNumber(context, number.toString())

    fun formatNumber(context: Context, number: Float): String =
        formatNumber(context, number.toString())

    fun formatNumber(context: Context, number: Double): String =
        formatNumber(context, number.toString())
}
