package com.example.reg.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.reg.data.model.HostelInfo
import com.example.reg.data.model.LibraryRequest
import com.example.reg.data.model.Payment
import com.example.reg.data.model.Student
import com.example.reg.data.repository.RegistrationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RegistrationRepository(application)

    private val _isOnline = MutableStateFlow(false)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    private val _studentResult = MutableStateFlow<String?>(null)
    val studentResult: StateFlow<String?> = _studentResult.asStateFlow()

    private val _libraryResult = MutableStateFlow<String?>(null)
    val libraryResult: StateFlow<String?> = _libraryResult.asStateFlow()

    private val _hostelResult = MutableStateFlow<String?>(null)
    val hostelResult: StateFlow<String?> = _hostelResult.asStateFlow()

    private val _paymentResult = MutableStateFlow<String?>(null)
    val paymentResult: StateFlow<String?> = _paymentResult.asStateFlow()

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    private val _libraryRequests = MutableStateFlow<List<LibraryRequest>>(emptyList())
    val libraryRequests: StateFlow<List<LibraryRequest>> = _libraryRequests.asStateFlow()

    private val _hostelInfos = MutableStateFlow<List<HostelInfo>>(emptyList())
    val hostelInfos: StateFlow<List<HostelInfo>> = _hostelInfos.asStateFlow()

    private val _payments = MutableStateFlow<List<Payment>>(emptyList())
    val payments: StateFlow<List<Payment>> = _payments.asStateFlow()

    init {
        checkConnectivity()
    }

    fun checkConnectivity() {
        _isOnline.value = repository.getConnectivityStatus()
    }

    fun registerStudent(
        fullName: String, idNo: String, regNumber: String,
        gender: String, course: String, department: String
    ) {
        val student = Student(
            fullName = fullName,
            idNo = idNo,
            regNumber = regNumber,
            gender = gender,
            course = course,
            department = department
        )
        viewModelScope.launch {
            repository.saveStudent(student) { success ->
                _studentResult.value = if (success) "Student registered successfully" else "Failed to register student"
                if (success) {
                    loadStudents()
                }
            }
        }
    }

    fun requestLibraryBook(
        isbn: String, author: String, title: String,
        dateBorrowed: String, dateReturn: String, studentIdNo: String
    ) {
        val request = LibraryRequest(
            isbn = isbn,
            author = author,
            title = title,
            dateBorrowed = dateBorrowed,
            dateReturn = dateReturn,
            studentIdNo = studentIdNo
        )
        viewModelScope.launch {
            repository.saveLibraryRequest(request) { success ->
                _libraryResult.value = if (success) "Library request submitted" else "Failed to submit library request"
                if (success) {
                    loadLibraryRequests()
                }
            }
        }
    }

    fun saveHostelInfo(
        location: String, roomType: String, sharable: Boolean, studentIdNo: String
    ) {
        val info = HostelInfo(
            location = location,
            roomType = roomType,
            sharable = sharable,
            studentIdNo = studentIdNo
        )
        viewModelScope.launch {
            repository.saveHostelInfo(info) { success ->
                _hostelResult.value = if (success) "Hostel info saved" else "Failed to save hostel info"
                if (success) {
                    loadHostelInfos()
                }
            }
        }
    }

    fun processPayment(
        phoneNumber: String, amount: String, studentIdNo: String
    ) {
        val payment = Payment(
            phoneNumber = phoneNumber,
            amount = amount,
            status = "completed",
            studentIdNo = studentIdNo
        )
        viewModelScope.launch {
            repository.savePayment(payment) { success ->
                _paymentResult.value = if (success) "Payment confirmed - registration complete" else "Payment failed"
                if (success) {
                    loadPayments()
                }
            }
        }
    }

    fun loadStudents() {
        repository.getAllStudents { list ->
            _students.value = list
        }
    }

    fun loadLibraryRequests() {
        repository.getAllLibraryRequests { list ->
            _libraryRequests.value = list
        }
    }

    fun loadHostelInfos() {
        repository.getAllHostelInfo { list ->
            _hostelInfos.value = list
        }
    }

    fun loadPayments() {
        repository.getAllPayments { list ->
            _payments.value = list
        }
    }

    fun loadAllData() {
        loadStudents()
        loadLibraryRequests()
        loadHostelInfos()
        loadPayments()
    }
}
