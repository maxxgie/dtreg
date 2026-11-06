package com.example.reg.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reg.ui.viewmodel.RegistrationViewModel

@Composable
fun HostelInfoScreen(viewModel: RegistrationViewModel = viewModel()) {
    var location by remember { mutableStateOf("") }
    var roomType by remember { mutableStateOf("") }
    var sharable by remember { mutableStateOf(false) }
    var studentIdNo by remember { mutableStateOf("") }

    val roomTypes = listOf("Bedsitter", "1 Bedroom", "2 Bedroom", "Studio", "Shared Room")
    val result by viewModel.hostelResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Hostel Accommodation",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Select your hostel preferences",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Hostel Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Room Type", style = MaterialTheme.typography.bodyMedium)
        DropdownMenu(
            expanded = roomType.isNotEmpty(),
            onDismissRequest = { roomType = "" },
        ) {
            roomTypes.forEach { r ->
                DropdownMenuItem(
                    text = { Text(r) },
                    onClick = { roomType = r }
                )
            }
        }
        OutlinedTextField(
            value = roomType,
            onValueChange = { roomType = it },
            label = { Text("Select Room Type") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            }
        )

        Text(text = "Sharable Room", style = MaterialTheme.typography.bodyMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                checked = sharable,
                onCheckedChange = { sharable = it }
            )
            Text(text = if (sharable) "Yes" else "No")
        }

        OutlinedTextField(
            value = studentIdNo,
            onValueChange = { studentIdNo = it },
            label = { Text("Student ID Number") },
            modifier = Modifier.fillMaxWidth()
        )

        if (result != null) {
            Text(
                text = result!!,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Button(
            onClick = {
                viewModel.saveHostelInfo(location, roomType, sharable, studentIdNo)
            },
            enabled = location.isNotBlank() && roomType.isNotBlank() && studentIdNo.isNotBlank()
        ) {
            Text("Save Hostel Info")
        }
    }
}
