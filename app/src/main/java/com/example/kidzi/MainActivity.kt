package com.example.kidzi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.kidzi.databinding.ActivityMainBinding
import com.example.kidzi.di.db.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sharedPreferences: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up navigation with conditional start destination
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)

        Log.i("StartupDebug", "Level: $sharedPreferences.getLevel(), CanOpen: $sharedPreferences.canOpen()")

        val level = sharedPreferences.getLevel()
        val canOpen = sharedPreferences.canOpen()

        val startDestination = when {
            !canOpen -> R.id.navigation_home
            level == 0 -> R.id.parentLoginFragment
            level == 1 -> R.id.parentInfoIntroFragment
            level == 2 -> R.id.kidIntroFragment
            level == 3 -> R.id.familyDisFirstFragment
            level == 4 -> R.id.mainFragment
            else -> R.id.parentLoginFragment
        }

        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
    }
}
