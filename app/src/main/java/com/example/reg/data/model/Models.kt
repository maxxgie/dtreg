package com.example.reg.data.model

data class Student(
    val fullName: String = "",
    val idNo: String = "",
    val regNumber: String = "",
    val gender: String = "",
    val course: String = "",
    val department: String = "",
    val paymentConfirmed: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

data class LibraryRequest(
    val isbn: String = "",
    val author: String = "",
    val title: String = "",
    val dateBorrowed: String = "",
    val dateReturn: String = "",
    val studentIdNo: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

data class HostelInfo(
    val location: String = "",
    val roomType: String = "",
    val sharable: Boolean = false,
    val studentIdNo: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

data class Payment(
    val phoneNumber: String = "",
    val amount: String = "",
    val status: String = "pending",
    val studentIdNo: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
