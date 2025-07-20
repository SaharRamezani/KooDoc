package com.example.kidzi.util

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kidzi.di.db.PreferenceManager
import java.util.Locale

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(base: Context) {
        val lang = PreferenceManager(base).getLanguage() ?: "fa"
        val newBase = MyLanguageManager.setLocale(base, lang)
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lang = MyLanguageManager.getLanguage(this)
        if (lang == "fa") {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        } else {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
    }

    private fun updateLocale(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return context.createConfigurationContext(config)
    }
}