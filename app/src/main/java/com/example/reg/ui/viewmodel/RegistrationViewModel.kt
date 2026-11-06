package com.example.reg.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reg.data.model.LibraryRequest
import com.example.reg.data.repository.RegistrationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(private val repository: RegistrationRepository = RegistrationRepository()) : ViewModel() {
    private val _libraryResult = MutableStateFlow<String?>(null)
    val libraryResult: StateFlow<String?> = _libraryResult.asStateFlow()

    private val _libraryRequests = MutableStateFlow<List<LibraryRequest>>(emptyList())
    val libraryRequests: StateFlow<List<LibraryRequest>> = _libraryRequests.asStateFlow()

    fun requestLibraryBook(isbn: String, author: String, title: String, dateBorrowed: String, dateReturn: String, studentIdNo: String) {
        val request = LibraryRequest(isbn = isbn, author = author, title = title, dateBorrowed = dateBorrowed, dateReturn = dateReturn, studentIdNo = studentIdNo)
        repository.saveLibraryRequest(request) { success ->
            _libraryResult.value = if (success) "Library request submitted" else "Failed to submit library request"
            if (success) loadLibraryRequests()
        }
    }

    fun loadLibraryRequests() {
        repository.getAllLibraryRequests { list -> _libraryRequests.value = list }
    }
}