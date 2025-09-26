package com.example.alcoholtracker.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
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

    private val amountProgressBarTarget = context.dataStore.data
        .map { preferences ->
            preferences[AMOUNT_PROGRESS_BAR_TARGET] ?: 1000.0
        }

    suspend fun updateAmountTarget(target: Double){
        context.dataStore.edit { preferences ->
            preferences[AMOUNT_PROGRESS_BAR_TARGET] = target
        }
    }

    fun getAmountTarget(): Flow<Double> {
        return amountProgressBarTarget
    }

    private val countProgressBarTarget = context.dataStore.data
        .map { preferences ->
            preferences[COUNT_PROGRESS_BAR_TARGET] ?: 5.0
        }

    suspend fun updateCountTarget(target: Double){
        context.dataStore.edit { preferences ->
            preferences[COUNT_PROGRESS_BAR_TARGET] = target
        }
    }

    fun getCountTarget(): Flow<Double> {
        return countProgressBarTarget
    }

    private val moneyProgressBarTarget = context.dataStore.data
        .map { preferences ->
            preferences[MONEY_PROGRESS_BAR_TARGET] ?: 10.0
        }

    fun getMoneyTarget(): Flow<Double> {
        return moneyProgressBarTarget
    }

    suspend fun updateMoneyTarget(target: Double){
        context.dataStore.edit { preferences ->
            preferences[MONEY_PROGRESS_BAR_TARGET] = target
        }
    }

    suspend fun updateType(type: String){
        context.dataStore.edit { preferences ->
            preferences[PROGRESS_BAR_TYPE] = type
        }

    }

    companion object{
        val PROGRESS_BAR_TYPE = stringPreferencesKey("progress_bar_type")
        val AMOUNT_PROGRESS_BAR_TARGET = doublePreferencesKey("amount_progress_bar_target")
        val COUNT_PROGRESS_BAR_TARGET = doublePreferencesKey("count_progress_bar_target")
        val MONEY_PROGRESS_BAR_TARGET = doublePreferencesKey("money_progress_bar_target")


    }
}