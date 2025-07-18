package com.example.kidzi

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.example.kidzi.databinding.ActivityMainBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.util.BaseActivity
import com.example.kidzi.util.MyLanguageManager.getLanguage
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject
import android.content.res.Configuration


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sharedPreferences: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lang = "fa"
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Set status bar to transparent manually
        window.statusBarColor = Color.TRANSPARENT

        // Set up navigation with conditional start destination
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)

        Log.i("StartupDebug", "Level: $sharedPreferences.getLevel(), CanOpen: $sharedPreferences.canOpen()")

        val level = sharedPreferences.getLevel()

        val startDestination = when {
            level == 0 -> R.id.navigation_home
            level == 1 -> R.id.parentLoginFragment
            level == 2 -> R.id.parentInfoIntroFragment
            level == 3 -> R.id.kidIntroFragment
            else -> R.id.mainFragment
        }

        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
    }
}