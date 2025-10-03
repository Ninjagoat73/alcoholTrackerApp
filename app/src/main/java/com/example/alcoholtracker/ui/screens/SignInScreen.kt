package com.example.alcoholtracker.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.alcoholtracker.ui.components.ArcBackground
import com.example.alcoholtracker.ui.viewmodel.AuthViewModel


@Composable
fun SignInScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onGuestLogin: () -> Unit = { authViewModel.signInAnonymously() }
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    val isFormValid = email.isNotBlank() && password.length >= 6
    val context = LocalContext.current

    ArcBackground()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {



        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )



        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Rounded.Done else Icons.Default.Check,
                        contentDescription = null
                    )
                }
            },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                authViewModel.signIn(email, password) { success ->
                    if (!success) Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                }},
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid
        ) {
            Text("Login")
        }

        Button(
            onClick = onGuestLogin,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Text("Continue as Guest")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Don't have an account? Register")
        }


    }
}
