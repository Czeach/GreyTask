package com.czech.greytask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.czech.greytask.R
import com.czech.greytask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.apply {
            bottomNav.setupWithNavController(navController)

            setSupportActionBar(toolbar)

            appBarConfig = AppBarConfiguration(
                topLevelDestinationIds = setOf(
                    R.id.homeFragment,
                    R.id.repositoriesFragment,
                    R.id.usersFragment
                )
            )
            setupActionBarWithNavController(navController, appBarConfig)
        }
    }
}