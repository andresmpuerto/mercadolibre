package com.andrempuerto.meli.repository

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.andrempuerto.meli.data.source.local.CountryPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class CountrySiteRepository
@Inject constructor(@ApplicationContext context: Context) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "country")

    private object PreferencesKeys {
        val SITE_ID = preferencesKey<String>("site_id")
    }

    val siteIdFlow: Flow<CountryPreference> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val siteId = preferences[PreferencesKeys.SITE_ID] ?: "MCO"
            CountryPreference(siteId = siteId)
        }

    suspend fun updateSiteId(siteId: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SITE_ID] = siteId
        }
    }

}

