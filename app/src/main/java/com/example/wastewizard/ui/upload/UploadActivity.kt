package com.example.wastewizard.ui.upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wastewizard.databinding.ActivityUploadBinding
import com.example.wastewizard.ui.klasifikasi.KlasifikasiActivity

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            val intent = Intent(this,KlasifikasiActivity::class.java)
            startActivity(intent)
        }

        val imageUri: Uri? = intent.getParcelableExtra("imageUri")

        showImage(imageUri)

        imageUri?.let { uri ->
            binding.imagePreview.setImageURI(uri)
        }

        val filePath: String? = imageUri?.let { uri ->
            if (uri.scheme == "file") {
                uri.path
            } else {
                // Jika URI adalah content://, Anda mungkin perlu mengonversinya menjadi path file
                // Misalnya, dengan bantuan metode getContentResolver().query()
                // Tapi ini hanya contoh dan Anda perlu mengimplementasikannya sesuai kebutuhan
                null
            }
        }

        // Lanjutkan dengan implementasi logika pengiriman gambar ke server atau pemrosesan lainnya
        // ...
    }
    private fun showImage(uri: Uri?) {
        if (uri != null) {
            binding.imagePreview.setImageURI(uri)
        }
    }

}