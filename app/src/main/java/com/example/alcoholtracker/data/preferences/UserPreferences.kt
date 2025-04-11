package com.example.alcoholtracker.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor (
    @ApplicationContext private val context: Context
){
    val Context.dataStore by preferencesDataStore(name = "user_preferences")

    val progressBarType = context.dataStore.data
        .map { preferences ->
            preferences[PROGRESS_BAR_TYPE] ?: "MONEY"
        }

    val progressBarTarget = context.dataStore.data
        .map { preferences ->
            preferences[PROGRESS_BAR_TARGET] ?: 0.0
        }

    suspend fun updateTarget(target: Double){
        context.dataStore.edit { preferences ->
            preferences[PROGRESS_BAR_TARGET] = target
        }
    }

    suspend fun updateType(type: String){
        context.dataStore.edit { preferences ->
            preferences[PROGRESS_BAR_TYPE] = type
        }

    }

    companion object{
        val PROGRESS_BAR_TYPE = stringPreferencesKey("progress_bar_type")
        val PROGRESS_BAR_TARGET = doublePreferencesKey("progress_bar_target")

    }
}