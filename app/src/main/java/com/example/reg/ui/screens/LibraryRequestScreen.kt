package com.example.reg.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reg.ui.viewmodel.RegistrationViewModel

@Composable
fun LibraryRequestScreen(viewModel: RegistrationViewModel = viewModel()) {
    var isbn by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var dateBorrowed by remember { mutableStateOf("") }
    var dateReturn by remember { mutableStateOf("") }
    var studentIdNo by remember { mutableStateOf("") }

    val result by viewModel.libraryResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Library Book Request",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Request a book from the university library",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = isbn,
            onValueChange = { isbn = it },
            label = { Text("Book ISBN") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Author") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = dateBorrowed,
            onValueChange = { dateBorrowed = it },
            label = { Text("Date Borrowed (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = dateReturn,
            onValueChange = { dateReturn = it },
            label = { Text("Date of Return (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )
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
                viewModel.requestLibraryBook(isbn, author, title, dateBorrowed, dateReturn, studentIdNo)
            },
            enabled = isbn.isNotBlank() && author.isNotBlank() && title.isNotBlank() && dateBorrowed.isNotBlank() && dateReturn.isNotBlank() && studentIdNo.isNotBlank()
        ) {
            Text("Submit Request")
        }
    }
}
