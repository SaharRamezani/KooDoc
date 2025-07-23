package com.example.kidzi.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

object MyLanguageManager {

    private const val PREF_NAME = "language_pref"
    private const val KEY_LANGUAGE = "app_language"
    private const val DEFAULT_LANGUAGE = "en"

    /** Retrieve saved language (default is English) */
    fun getLanguage(context: Context? = null): String {
        return context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            ?.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE)
            ?: DEFAULT_LANGUAGE
    }
}