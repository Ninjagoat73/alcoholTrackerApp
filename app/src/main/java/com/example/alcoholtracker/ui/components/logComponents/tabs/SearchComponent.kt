package com.example.alcoholtracker.ui.components.logComponents.tabs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchComponent(
    onCameraClick: () -> Unit,
    onTyped: (String) -> Unit
) {

    var value by remember { mutableStateOf("") }


    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                value = it
                onTyped(it)
            },
            placeholder = { Text("What did you have for a drink?") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                IconButton(
                    onClick = { onCameraClick() }
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Camera")
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}
