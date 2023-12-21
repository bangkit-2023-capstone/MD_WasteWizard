package com.example.wastewizard.data

import android.util.Log
import androidx.lifecycle.liveData
import com.example.wastewizard.ResultState
import com.example.wastewizard.data.pref.UserModel
import com.example.wastewizard.data.pref.UserPreference
import com.example.wastewizard.data.response.ErrorResponse
import com.example.wastewizard.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }

    fun userRegister(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val message = apiService.register(name, email, password)
            emit(ResultState.Success(message))
            Log.d("Register", "Register berhasil $name, $email, $password")
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { ResultState.Error(it) })
            Log.d(
                "Register",
                "Register gagal $name, $email, $password, ${e.message} dan $errorMessage"
            )
        }
    }

    fun userRefresh(token: String) = liveData {
        emit(ResultState.Loading)
        try {
            val message = apiService.refresh(token)
            emit(ResultState.Success(message))
            Log.d("Refresh", "Refresh Berhasil $message")
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { ResultState.Error(it) })
            Log.d("Refresh", " Stories On Failure $errorMessage")
        }
    }

    fun userProfile(token: String) = liveData {
        emit(ResultState.Loading)
        try {
            val message = apiService.profile(token)
            emit(ResultState.Success(message))
            Log.d("Profile", "Profile Ditemukan $message")
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { ResultState.Error(it) })
            Log.d("Profile", " Profile tidak ditemukan $errorMessage")
        }
    }

    fun userLogout(token: String) = liveData {
        emit(ResultState.Loading)
        try {
            val message = apiService.logout(token)
            emit(ResultState.Success(message))
            Log.d("Logout", "Logout Berhasil $message")
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { ResultState.Error(it) })
            Log.d("Logout", "Logout gagal $errorMessage")
        }
    }

    fun userLogin(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val message = apiService.login(email, password)
            emit(ResultState.Success(message))
            Log.d("Login", "Login Berhasil $email, $password")
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { ResultState.Error(it) })
            Log.d(
                "Login",
                "Login gagal $email, $password, ${e.message} dan $errorMessage"
            )
        }
    }

    fun uploadStory(file: MultipartBody.Part, klasifikasi: String, data: String) = liveData {
        emit(ResultState.Loading)
        try {
            val message = apiService.uploadImage(file, klasifikasi, data)
            emit(ResultState.Success(message))
            Log.d("Upload Story", "Upload Story Success $message")
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(errorMessage?.let { ResultState.Error(it) })
            Log.d("Upload Story", " Upload On Failure $errorMessage")
        }
    }


//    suspend fun saveSession(user: UserModel) {
//        userPreference.saveSession(user)
//    }
//
//    fun getSession(): Flow<UserModel> {
//        return userPreference.getSession()
//    }
//
//    suspend fun logout() {
//        userPreference.logout()
//    }

}