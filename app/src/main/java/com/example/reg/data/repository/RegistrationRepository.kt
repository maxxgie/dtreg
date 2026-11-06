package com.example.reg.data.repository

import com.example.reg.data.model.LibraryRequest
import com.google.firebase.database.FirebaseDatabase

class RegistrationRepository {
    private val database = FirebaseDatabase.getInstance().reference

    fun saveLibraryRequest(request: LibraryRequest, onComplete: (Boolean) -> Unit) {
        val ref = database.child("library_requests").push()
        ref.setValue(request.copy(id = ref.key)).addOnCompleteListener { onComplete(it.isSuccessful) }
    }

    fun getAllLibraryRequests(onComplete: (List<LibraryRequest>) -> Unit) {
        database.child("library_requests").get().addOnSuccessListener { snap ->
            val list = mutableListOf<LibraryRequest>()
            for (child in snap.children) {
                child.getValue(LibraryRequest::class.java)?.let { list.add(it) }
            }
            onComplete(list)
        }.addOnFailureListener { onComplete(emptyList()) }
    }
}