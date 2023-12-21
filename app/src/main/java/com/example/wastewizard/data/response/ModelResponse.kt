package com.example.wastewizard.data.response

import com.google.gson.annotations.SerializedName

data class ModelResponse(
    @field:SerializedName("klasifikasi")
    val klasifikasi: String,

    @field:SerializedName("data")
    val data: String
)
