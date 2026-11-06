package com.example.reg.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.reg.data.model.HostelInfo
import com.example.reg.data.model.LibraryRequest
import com.example.reg.data.model.Payment
import com.example.reg.data.model.Student
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationRepository(private val context: Context) {

    private val database = FirebaseDatabase.getInstance()

    init {
        database.setPersistenceEnabled(true)
    }

    private fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        if (network == null) return false
        val capabilities = cm.getNetworkCapabilities(network)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

    fun saveStudent(student: Student, onComplete: (Boolean) -> Unit) {
        val ref = database.getReference("students").child(student.idNo)
        ref.setValue(student).addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }
    }

    fun saveLibraryRequest(request: LibraryRequest, onComplete: (Boolean) -> Unit) {
        val ref = database.getReference("library_requests").push()
        ref.setValue(request).addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }
    }

    fun saveHostelInfo(info: HostelInfo, onComplete: (Boolean) -> Unit) {
        val ref = database.getReference("hostel_info").child(info.studentIdNo)
        ref.setValue(info).addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }
    }

    fun savePayment(payment: Payment, onComplete: (Boolean) -> Unit) {
        val ref = database.getReference("payments").child(payment.studentIdNo)
        ref.setValue(payment).addOnCompleteListener { task ->
            onComplete(task.isSuccessful)
        }
    }

    fun getStudent(idNo: String, onComplete: (Student?) -> Unit) {
        database.getReference("students").child(idNo).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val student = snapshot.getValue(Student::class.java)
                    onComplete(student)
                }

                override fun onCancelled(error: DatabaseError) {
                    onComplete(null)
                }
            }
        )
    }

    fun getAllStudents(onComplete: (List<Student>) -> Unit) {
        database.getReference("students").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val students = mutableListOf<Student>()
                    for (child in snapshot.children) {
                        val student = child.getValue(Student::class.java)
                        if (student != null) {
                            students.add(student)
                        }
                    }
                    onComplete(students)
                }

                override fun onCancelled(error: DatabaseError) {
                    onComplete(emptyList())
                }
            }
        )
    }

    fun getAllLibraryRequests(onComplete: (List<LibraryRequest>) -> Unit) {
        database.getReference("library_requests").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val requests = mutableListOf<LibraryRequest>()
                    for (child in snapshot.children) {
                        val request = child.getValue(LibraryRequest::class.java)
                        if (request != null) {
                            requests.add(request)
                        }
                    }
                    onComplete(requests)
                }

                override fun onCancelled(error: DatabaseError) {
                    onComplete(emptyList())
                }
            }
        )
    }

    fun getAllHostelInfo(onComplete: (List<HostelInfo>) -> Unit) {
        database.getReference("hostel_info").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val infos = mutableListOf<HostelInfo>()
                    for (child in snapshot.children) {
                        val info = child.getValue(HostelInfo::class.java)
                        if (info != null) {
                            infos.add(info)
                        }
                    }
                    onComplete(infos)
                }

                override fun onCancelled(error: DatabaseError) {
                    onComplete(emptyList())
                }
            }
        )
    }

    fun getAllPayments(onComplete: (List<Payment>) -> Unit) {
        database.getReference("payments").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val payments = mutableListOf<Payment>()
                    for (child in snapshot.children) {
                        val payment = child.getValue(Payment::class.java)
                        if (payment != null) {
                            payments.add(payment)
                        }
                    }
                    onComplete(payments)
                }

                override fun onCancelled(error: DatabaseError) {
                    onComplete(emptyList())
                }
            }
        )
    }

    fun getConnectivityStatus(): Boolean = isOnline()
}
