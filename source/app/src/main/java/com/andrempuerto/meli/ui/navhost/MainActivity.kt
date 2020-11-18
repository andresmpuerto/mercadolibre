package com.andrempuerto.meli.ui.navhost

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.andrempuerto.meli.BuildConfig
import com.andrempuerto.meli.R
import com.andrempuerto.meli.databinding.ActivityMainBinding
import com.andrempuerto.meli.ui.settings.SettingsFragment.Companion.PREFERENCE_DARK_MODE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setNavigation()
        PreferenceManager.getDefaultSharedPreferences(this).also {preference ->
            if (preference.getBoolean(PREFERENCE_DARK_MODE, false)) {
                updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.version = BuildConfig.VERSION_NAME

        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = destination.label
            when (destination.id) {
                R.id.nav_home -> {
                    supportActionBar?.apply {
                        setDisplayShowHomeEnabled(false)
                        setDisplayHomeAsUpEnabled(false)
                    }
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                R.id.view_list_products -> {
                    supportActionBar?.apply {
                        setDisplayShowHomeEnabled(true)
                        setDisplayHomeAsUpEnabled(true)
                    }
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        return true
    }

    private fun setNavigation() {
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.view_list_products,
                R.id.nav_settings
            ),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
