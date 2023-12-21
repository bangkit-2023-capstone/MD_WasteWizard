package com.example.wastewizard.ui.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wastewizard.data.response.ModelResponse
import com.example.wastewizard.databinding.ActivityResultBinding
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive the intent data
        val resultJson = intent.getStringExtra("result")

        // Convert JSON string to ModelResponse
        val modelResponse = Gson().fromJson(resultJson, ModelResponse::class.java)

        // Access the values from ModelResponse
        val klasifikasi = modelResponse?.Klasifikasi ?: "Unknown"
//        val data = modelResponse?.data ?: "Unknown"

        // Display the result in the TextView
//        binding.hasilKlasifikasi.text = "Klasifikasi: $klasifikasi\nData: $data"
        binding.hasilKlasifikasi.text = klasifikasi
    }
}