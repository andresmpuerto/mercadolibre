package com.andrempuerto.meli.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.andrempuerto.meli.R

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    private var preference: SwitchPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        preference = findPreference(PREFERENCE_DARK_MODE)
        preference?.onPreferenceChangeListener = this
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        return true
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val mode = AppCompatDelegate.getDefaultNightMode()
        if (newValue == true && mode == AppCompatDelegate.MODE_NIGHT_NO) {
            updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
        } else if (newValue == false && mode == AppCompatDelegate.MODE_NIGHT_YES) {
            updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
        }
        return true
    }

    companion object {
        const val PREFERENCE_DARK_MODE = "dark_mode"
    }
}