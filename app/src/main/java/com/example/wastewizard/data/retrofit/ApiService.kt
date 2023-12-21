package com.example.wastewizard.data.retrofit

import com.example.wastewizard.data.response.LoginResponse
import com.example.wastewizard.data.response.LogoutResponse
import com.example.wastewizard.data.response.ModelResponse
import com.example.wastewizard.data.response.ProfileResponse
import com.example.wastewizard.data.response.RefreshResponse
import com.example.wastewizard.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("logout")
    suspend fun logout(
        @Header("token") token: String,
    ): LogoutResponse

    @FormUrlEncoded
    @POST("me")
    suspend fun profile(
        @Field("token") token: String
    ): ProfileResponse

    @FormUrlEncoded
    @POST("refresh")
    suspend fun refresh(
        @Field("token") token: String
    ): RefreshResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<ModelResponse>

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<UploadResponse>
}