package com.example.alcoholtracker.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    fun updateUser(user: FirebaseUser?) {
        _userId.value = user?.uid
    }

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _userId.value = firebaseAuth.currentUser?.uid
        }

        updateUser(FirebaseAuth.getInstance().currentUser)
    }

    fun signIn(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUser(auth.currentUser)
                    onResult(task.isSuccessful)
                } else {
                    onResult(false)
                }
                }
    }

    fun createAccount(email: String, password: String, onResult: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUser(FirebaseAuth.getInstance().currentUser)
                    onResult(true)
                } else {
                    onResult(false)
                }
            }
    }
    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _userId.value = null
    }

    fun signInAnonymously() {
        viewModelScope.launch {
            try {
                auth.signInAnonymously().await()
                _userId.value = auth.currentUser?.uid
            } catch (e: Exception) {
                Log.e("Auth", "Anonymous sign-in failed", e)
            }
        }
    }

    init {

    }

    @Composable
    fun getUserID(): State<String?> {
        return userId.collectAsState()
    }

}