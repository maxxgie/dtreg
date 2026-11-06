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
fun RegistrationScreen(viewModel: RegistrationViewModel = viewModel()) {
    var fullName by remember { mutableStateOf("") }
    var idNo by remember { mutableStateOf("") }
    var regNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var course by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }

    val courses = listOf("Computer Science", "Information Technology", "Business Administration", "Engineering", "Medicine", "Law", "Education")
    val departments = listOf("School of Computing", "School of Business", "School of Engineering", "School of Medicine", "School of Law", "School of Education")
    val genders = listOf("Male", "Female", "Other")

    val result by viewModel.studentResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Student Registration",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Register as a first-year student",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Names") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = idNo,
            onValueChange = { idNo = it },
            label = { Text("ID Number") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = regNumber,
            onValueChange = { regNumber = it },
            label = { Text("Registration Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Gender", style = MaterialTheme.typography.bodyMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            genders.forEach { g ->
                RadioButton(
                    selected = gender == g,
                    onClick = { gender = g },
                    colors = RadioButtonDefaults.colors()
                )
                Text(text = g)
            }
        }

        Text(text = "Course", style = MaterialTheme.typography.bodyMedium)
        DropdownMenu(
            expanded = course.isNotEmpty(),
            onDismissRequest = { course = "" },
        ) {
            courses.forEach { c ->
                DropdownMenuItem(
                    text = { Text(c) },
                    onClick = { course = c }
                )
            }
        }
        OutlinedTextField(
            value = course,
            onValueChange = { course = it },
            label = { Text("Select Course") },
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

        Text(text = "Department", style = MaterialTheme.typography.bodyMedium)
        DropdownMenu(
            expanded = department.isNotEmpty(),
            onDismissRequest = { department = "" },
        ) {
            departments.forEach { d ->
                DropdownMenuItem(
                    text = { Text(d) },
                    onClick = { department = d }
                )
            }
        }
        OutlinedTextField(
            value = department,
            onValueChange = { department = it },
            label = { Text("Select Department") },
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

        if (result != null) {
            Text(
                text = result!!,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Button(
            onClick = {
                viewModel.registerStudent(fullName, idNo, regNumber, gender, course, department)
            },
            enabled = fullName.isNotBlank() && idNo.isNotBlank() && regNumber.isNotBlank() && course.isNotBlank() && department.isNotBlank()
        ) {
            Text("Register")
        }
    }
}
