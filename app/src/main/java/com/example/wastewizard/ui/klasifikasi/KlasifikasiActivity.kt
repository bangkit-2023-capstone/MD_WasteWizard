package com.example.wastewizard.ui.klasifikasi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.wastewizard.databinding.ActivityKlasifikasiBinding
import com.example.wastewizard.ui.main.DashboardActivity
import com.example.wastewizard.ui.upload.UploadActivity

class KlasifikasiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKlasifikasiBinding
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKlasifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            val intentBack = Intent(this@KlasifikasiActivity, DashboardActivity::class.java)
            startActivity(intentBack)
        }

        binding.btnGaleri.setOnClickListener{
            startGallery()
        }
        binding.btnScan.setOnClickListener{
            startScan()
        }
    }
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        Toast.makeText(this, "Tombol Buka Gallery di tekan", Toast.LENGTH_SHORT).show()
    }
    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                val intent = Intent(this, UploadActivity::class.java)
                intent.putExtra("imageUri", uri)
                startActivity(intent)
            } else {
                Log.d("Photo Picker", "No media selected")
            }
        }
    private fun startScan() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            currentImageUri = getImageUri(this)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri)
            launcherIntentCamera.launch(cameraIntent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val intent = Intent(this, UploadActivity::class.java)
            intent.putExtra("imageUri", currentImageUri)
            startActivity(intent)
        } else {
            // Penanganan jika pengguna membatalkan pengambilan gambar
            Log.d("Camera", "Canceled")
        }
    }
}