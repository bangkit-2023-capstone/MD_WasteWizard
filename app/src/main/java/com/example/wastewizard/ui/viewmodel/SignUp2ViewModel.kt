package com.example.wastewizard.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wastewizard.data.UserRepository

class SignUp2ViewModel(private val repository: UserRepository) : ViewModel() {
    fun userRegister(name: String, email: String, password: String) =
        repository.userRegister(name, email, password)
}