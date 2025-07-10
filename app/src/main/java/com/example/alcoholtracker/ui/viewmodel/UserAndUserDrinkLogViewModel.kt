package com.example.alcoholtracker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alcoholtracker.data.model.DrinkCategory
import com.example.alcoholtracker.data.model.User

import com.example.alcoholtracker.data.model.UserDrinkLog
import com.example.alcoholtracker.data.model.UserDrinkLogSummary
import com.example.alcoholtracker.data.repository.UserAndUserDrinkLogRepository
import com.example.alcoholtracker.domain.usecase.DrinkCreateRequest
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
import java.time.LocalDateTime
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
            userAndUserDrinkLogRepository.deleteDrinkLog(log)
        }
    }


    fun logDrink(request: DrinkCreateRequest){

        val drink = UserDrinkLog(
            drinkId = 2,
            userId = FirebaseAuth.getInstance().currentUser!!.uid,
            name = request.name,
            cost = request.cost,
            alcoholPercentage = request.abv,
            amount = request.volume,
            category = request.category,
            recipient = request.recipient,
            date = request.dateTime?: LocalDateTime.now()
        )


        viewModelScope.launch {
            userAndUserDrinkLogRepository.insertDrinkLog(drink)
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
