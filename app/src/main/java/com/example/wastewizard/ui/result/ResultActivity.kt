package com.example.wastewizard.ui.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wastewizard.data.response.ModelResponse
import com.example.wastewizard.databinding.ActivityResultBinding
import com.example.wastewizard.ui.klasifikasi.KlasifikasiActivity
import com.google.gson.Gson
import java.io.File

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

        val result = intent.getStringExtra("result")
        // Process the result as needed

        // Retrieve the compressed image file path from the intent
        val compressedImagePath = intent.getStringExtra("compressedImagePath")

        // Display the compressed image in the preview
        showCompressedImage(compressedImagePath)

        binding.btnScanUlang.setOnClickListener{
            val intentBack = Intent(this@ResultActivity, KlasifikasiActivity::class.java)
            startActivity(intentBack)
        }
    }

    private fun showCompressedImage(imagePath: String?) {
        if (imagePath != null) {
            val compressedImageFile = File(imagePath)
            binding.imagePreview.setImageURI(Uri.fromFile(compressedImageFile))
        }
    }
}