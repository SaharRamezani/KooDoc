package com.example.kidzi

import android.graphics.Color
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.kidzi.databinding.ActivityMainBinding
import com.example.kidzi.di.db.PreferenceManager
import com.example.kidzi.util.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var sharedPreferences: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.TRANSPARENT

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)

        val level = sharedPreferences.getLevel()

        val startDestination = when (level) {
            0 -> R.id.navigation_home
            1 -> R.id.parentLoginFragment
            2 -> R.id.parentInfoIntroFragment
            3 -> R.id.kidIntroFragment
            else -> R.id.mainFragment
        }

        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
    }
}