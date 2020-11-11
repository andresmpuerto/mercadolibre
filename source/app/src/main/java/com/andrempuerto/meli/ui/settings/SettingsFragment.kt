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
        preference = findPreference("dark_mode")
        preference?.onPreferenceChangeListener = this
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        if (this.preference?.isChecked == true){
            updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
        }
        return true
    }
}