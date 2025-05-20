package com.example.kidzi.di.helpers

import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import java.util.concurrent.TimeUnit

class PersianDateHelper {

    companion object {
        private const val DATE_PATTERN = "yyyy-MM-dd" // Adjust based on your stored format

        /**
         * Calculates the child's age in weeks from the given Persian birth date.
         */
        fun getAgeInWeeks(birthDate: String): Int {
            val birthPersianDate = PersianDateFormat(DATE_PATTERN).parse(birthDate)
            val currentPersianDate = PersianDate()

            // Calculate total days difference
            val daysDifference = currentPersianDate.shDay - birthPersianDate.shDay

            return daysDifference / 7
        }

        fun String.toEnglishDigits(): String {
            return this
                .replace('۰', '0')
                .replace('۱', '1')
                .replace('۲', '2')
                .replace('۳', '3')
                .replace('۴', '4')
                .replace('۵', '5')
                .replace('۶', '6')
                .replace('۷', '7')
                .replace('۸', '8')
                .replace('۹', '9')
        }

        /**
         * Calculates the child's age in months from the given Persian birth date.
         */
        fun getAgeInMonths(birthDate: String): Int {

            val parts = birthDate.split("-")

            // Extract year, month, and day (assuming format is "YYYY-M-D")
            val birthYear = parts[0].toInt()
            val birthMonth = parts[1].toInt()
            val day = parts[2].toInt()
            //val birthPersianDate = PersianDateFormat(DATE_PATTERN).parse(birthDate)
            val currentPersianDate = PersianDate()

            val currentYear = currentPersianDate.shYear
            val currentMonth = currentPersianDate.shMonth

            return (currentYear - birthYear) * 12 + (currentMonth - birthMonth)
        }
    }
}
