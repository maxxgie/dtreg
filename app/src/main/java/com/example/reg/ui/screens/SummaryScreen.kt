package com.example.reg.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reg.ui.viewmodel.RegistrationViewModel

@Composable
fun SummaryScreen(viewModel: RegistrationViewModel = viewModel()) {
    val students by viewModel.students.collectAsState()
    val libraryRequests by viewModel.libraryRequests.collectAsState()
    val hostelInfos by viewModel.hostelInfos.collectAsState()
    val payments by viewModel.payments.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Registration Summary",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Print summary of all registered students",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = { viewModel.loadAllData() }
        ) {
            Text("Refresh Data")
        }

        if (students.isNotEmpty()) {
            Text(text = "Registered Students (${students.size})", style = MaterialTheme.typography.titleMedium)
            for (student in students) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Name: ${student.fullName}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "ID No: ${student.idNo}")
                        Text(text = "Reg No: ${student.regNumber}")
                        Text(text = "Gender: ${student.gender}")
                        Text(text = "Course: ${student.course}")
                        Text(text = "Department: ${student.department}")
                        val isPaid = payments.any { it.studentIdNo == student.idNo && it.status == "completed" }
                        Text(text = "Payment: ${if (isPaid) "Confirmed" else "Pending"}")
                    }
                }
            }
        } else {
            Text(text = "No students registered yet.")
        }

        if (libraryRequests.isNotEmpty()) {
            Text(text = "Library Requests (${libraryRequests.size})", style = MaterialTheme.typography.titleMedium)
            for (req in libraryRequests) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "ISBN: ${req.isbn}")
                        Text(text = "Title: ${req.title}")
                        Text(text = "Author: ${req.author}")
                        Text(text = "Student ID: ${req.studentIdNo}")
                        Text(text = "Borrowed: ${req.dateBorrowed} | Return: ${req.dateReturn}")
                    }
                }
            }
        }

        if (hostelInfos.isNotEmpty()) {
            Text(text = "Hostel Accommodations (${hostelInfos.size})", style = MaterialTheme.typography.titleMedium)
            for (info in hostelInfos) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Location: ${info.location}")
                        Text(text = "Room Type: ${info.roomType}")
                        Text(text = "Sharable: ${if (info.sharable) "Yes" else "No"}")
                        Text(text = "Student ID: ${info.studentIdNo}")
                    }
                }
            }
        }
    }
}
