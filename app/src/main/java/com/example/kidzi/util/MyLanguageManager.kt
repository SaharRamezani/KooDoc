package com.example.kidzi.util

import android.content.Context
import android.content.SharedPreferences

object MyLanguageManager {

    private const val PREF_NAME = "language_pref"
    private const val KEY_LANGUAGE = "app_language"
    private const val DEFAULT_LANGUAGE = "en"

    fun setLanguage(context: Context, language: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, language).apply()
    }

    fun getLanguage(context: Context? = null): String {
        // If you ever call this without context, fall back to system language
        return context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            ?.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE)
            ?: DEFAULT_LANGUAGE
    }
}