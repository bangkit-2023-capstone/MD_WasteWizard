package com.example.wastewizard.di

import android.content.Context
import com.example.wastewizard.data.UserRepository
import com.example.wastewizard.data.pref.UserPreference
import com.example.wastewizard.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}