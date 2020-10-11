package com.project.firstkotlin.ui.createchat

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.firstkotlin.data.model.Message
import com.project.firstkotlin.data.model.User
import com.project.firstkotlin.data.repository.MessageRepository
import com.project.firstkotlin.data.repository.UserRepository
import kotlinx.android.synthetic.main.activity_createchat.*

class NewchatViewModel(application: Application) : ViewModel() {
    val userRepository = UserRepository()

//    fun loadDataFromFirebase(): LiveData<MutableList<String>> {
//        val mutableData = MutableLiveData<MutableList<String>>()
//        userRepository.getUserList().observeForever {
//            mutableData.value = it
//        }
//
//        return mutableData
//    }


    class NewchatViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewchatViewModel::class.java)) {
                return NewchatViewModel(application) as T
            }

            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}