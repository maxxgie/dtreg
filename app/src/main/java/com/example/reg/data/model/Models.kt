package com.example.reg.data.model

data class LibraryRequest(
    val id: String? = null,
    val isbn: String = "",
    val author: String = "",
    val title: String = "",
    val dateBorrowed: String = "",
    val dateReturn: String = "",
    val studentIdNo: String = ""
)