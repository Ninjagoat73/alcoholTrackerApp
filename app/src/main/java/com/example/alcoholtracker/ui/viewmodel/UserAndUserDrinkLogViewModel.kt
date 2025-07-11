package com.example.alcoholtracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alcoholtracker.data.model.User

import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.data.repository.UserAndUserDrinkLogRepository
import com.example.alcoholtracker.domain.usecase.DrinkCreateRequest
import com.example.alcoholtracker.domain.usecase.DrinkHandler
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAndUserDrinkLogViewModel @Inject constructor(
    private val userAndUserDrinkLogRepository: UserAndUserDrinkLogRepository,
    private val drinkHandler: DrinkHandler
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId
    private val _twoDaySummary = MutableStateFlow<List<UserDrinkLog>?>(null)
    private val _drinkLogs = MutableStateFlow<List<UserDrinkLog>>(emptyList())
    val twoDaySummary: StateFlow<List<UserDrinkLog>?> = _twoDaySummary


    fun getDrinkLogs(userId: String): StateFlow<List<UserDrinkLog>>{
        return userAndUserDrinkLogRepository.getDrinkLogsByUserId(userId)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }


    fun setUserId(userId: String) {
        println("Setting userId: $userId")
        _userId.value = userId
        val user = FirebaseAuth.getInstance().currentUser

        viewModelScope.launch {
            val localUser = userAndUserDrinkLogRepository.getUserById(userId)
            if (localUser == null) {
                val newUser = User(
                    userId,
                    user?.displayName ?:"",
                    user?.email?:""
                )
                userAndUserDrinkLogRepository.insertUser(newUser)
            }
        }
    }


    fun getTwoDaySummary(userId: String): StateFlow<List<UserDrinkLog>> {
        return userAndUserDrinkLogRepository.getTwoDayLogsByUser(userId)
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun deleteDrink(log: UserDrinkLog){
        viewModelScope.launch {
            drinkHandler.deleteDrink(log)
        }
    }


    fun logDrink(request: DrinkCreateRequest){

        viewModelScope.launch {
            drinkHandler.createDrink(request)
        }
    }

    fun updateDrink(drinkToUpdate: UserDrinkLog, request: DrinkCreateRequest){
        viewModelScope.launch {
            drinkHandler.editDrink(drinkToUpdate, request)
        }
    }

    init { viewModelScope.launch {

        val currentUserId = auth.currentUser?.uid ?: ""
        if (currentUserId.isNotEmpty()) {
            _userId.value = currentUserId
            println("ViewModel created/restored. userId: $currentUserId")
        } else {
            println("Warning: No user logged in!")
        }
        auth.addAuthStateListener { firebaseAuth ->
            val newUserId = firebaseAuth.currentUser?.uid ?: ""
            if (newUserId != _userId.value) {
                _userId.value = newUserId
                println("Auth state changed. New userId: $newUserId")
            }
        }
    } }
}
