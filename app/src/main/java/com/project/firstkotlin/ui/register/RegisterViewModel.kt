package com.project.firstkotlin.ui.register

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.project.firstkotlin.data.model.User
import com.project.firstkotlin.data.model.UserSingleton
import com.project.firstkotlin.data.repository.Repository
import java.util.*

class RegisterViewModel : ViewModel() {
    private val repository = Repository.getInstance()

    val registerSuccessful = MutableLiveData<Boolean>()

    fun registerAccount(email: String, password: String, user: User, uri: Uri) =
        repository.registerAccount(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                registerSuccessful.value = true
                repository.addAccountToDatabase(user, uri)
            } else registerSuccessful.value = false
        }
}