package com.example.wastewizard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wastewizard.data.UserRepository
import com.example.wastewizard.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: UserRepository) : ViewModel(){
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }



    fun postLogin(email: String, password: String) = repository.userLogin(email, password)
}
