package com.example.alcoholtracker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alcoholtracker.data.model.User

import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.data.model.UserDrinkLogSummary
import com.example.alcoholtracker.data.repository.UserAndUserDrinkLogRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class UserAndUserDrinkLogViewModel @Inject constructor(
    private val userAndUserDrinkLogRepository: UserAndUserDrinkLogRepository,

) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId
    private val _twoDaySummary = MutableStateFlow<List<UserDrinkLog>?>(null)
    private val _drinkLogs = MutableStateFlow<List<UserDrinkLog>>(emptyList())
    val twoDaySummary: StateFlow<List<UserDrinkLog>?> = _twoDaySummary

    @OptIn(ExperimentalCoroutinesApi::class)
    val drinkLogs: StateFlow<List<UserDrinkLog>> = _userId
        .filter { it.isNotEmpty() } // Skip empty userId
        .flatMapLatest { userId ->
            userAndUserDrinkLogRepository.getDrinkLogsByUserId(userId)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


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


    fun getTwoDaySummary() {
        viewModelScope.launch {
            _userId.value?.let {
                _twoDaySummary.value = userAndUserDrinkLogRepository.getTwoDayLogs(it)
            }
        }
    }


    fun logDrink(drinkLog: UserDrinkLog){
        viewModelScope.launch {
            userAndUserDrinkLogRepository.insertDrinkLog(drinkLog)
        }
    }

    init { viewModelScope.launch {
        getTwoDaySummary()
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
