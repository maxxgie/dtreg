package com.example.reg.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reg.ui.viewmodel.RegistrationViewModel

@Composable
fun PaymentScreen(viewModel: RegistrationViewModel = viewModel()) {
    var phoneNumber by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var studentIdNo by remember { mutableStateOf("") }
    var showMpesaDialog by remember { mutableStateOf(false) }
    var mpesaConfirmed by remember { mutableStateOf(false) }

    val result by viewModel.paymentResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Fees Payment",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Pay fees via MPESA to complete your registration",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number (e.g. 0712345678)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Fees Amount (Kshs)") },
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
                if (phoneNumber.isNotBlank() && amount.isNotBlank() && studentIdNo.isNotBlank()) {
                    showMpesaDialog = true
                }
            },
            enabled = phoneNumber.isNotBlank() && amount.isNotBlank() && studentIdNo.isNotBlank()
        ) {
            Text("Pay via MPESA")
        }

        if (showMpesaDialog) {
            MpesaDialog(
                onConfirm = {
                    mpesaConfirmed = true
                    showMpesaDialog = false
                    viewModel.processPayment(phoneNumber, amount, studentIdNo)
                },
                onCancel = {
                    showMpesaDialog = false
                },
                phoneNumber = phoneNumber,
                amount = amount
            )
        }
    }
}

@Composable
fun MpesaDialog(
    phoneNumber: String,
    amount: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    var dialogResult by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text("MPESA Payment Prompt") },
        text = {
            Column {
                Text(text = "To: $phoneNumber")
                Text(text = "Amount: Kshs $amount")
                Text(text = "Enter your MPESA PIN on your phone to confirm payment.")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                dialogResult = "confirmed"
                onConfirm()
            }) {
                Text("Confirm Payment")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dialogResult = "cancelled"
                onCancel()
            }) {
                Text("Cancel")
            }
        }
    )

    if (dialogResult == "confirmed") {
        Text(
            text = "MPESA payment confirmed! Processing...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
