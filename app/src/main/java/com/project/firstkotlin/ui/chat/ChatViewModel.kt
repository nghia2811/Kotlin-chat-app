package com.project.firstkotlin.ui.chat

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.firstkotlin.data.repository.MessageRepository
import com.project.firstkotlin.data.model.Message

class ChatViewModel(application: Application) : ViewModel() {
    val chatRepository = MessageRepository()

    fun fetchMessages(): LiveData<MutableList<Message>> {
        val mutableData = MutableLiveData<MutableList<Message>>()
        chatRepository.getMessages().observeForever {
            mutableData.value = it
        }

        return mutableData
    }


    class ChatViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
                return ChatViewModel(application) as T
            }

            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}