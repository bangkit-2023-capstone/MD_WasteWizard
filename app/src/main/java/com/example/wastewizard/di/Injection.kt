package com.example.wastewizard.di

import android.content.Context
import com.example.wastewizard.data.UserRepository
import com.example.wastewizard.data.pref.UserPreference
import com.example.wastewizard.data.pref.dataStore
import com.example.wastewizard.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
}