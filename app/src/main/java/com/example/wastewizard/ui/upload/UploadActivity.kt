package com.example.wastewizard.ui.upload

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.wastewizard.R
import com.example.wastewizard.ResultState
import com.example.wastewizard.databinding.ActivityUploadBinding
import com.example.wastewizard.ui.ViewModelFactory
import com.example.wastewizard.ui.klasifikasi.KlasifikasiActivity
import com.example.wastewizard.ui.klasifikasi.uriToFile
import com.example.wastewizard.ui.viewmodel.UploadViewModel
import com.example.wastewizard.ui.welcome.MainActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private val storyModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var currentImageUri: Uri? = null
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val TAG = "AddUpload"
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri = data?.data
                // Handle the selected image URI (e.g., upload it to a server)
                // You can also use the URI to load the image into an ImageView
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityUploadBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnBack.setOnClickListener{
//            val intent = Intent(this,KlasifikasiActivity::class.java)
//            startActivity(intent)
//        }
//
//        val imageUri: Uri? = intent.getParcelableExtra("imageUri")
//
//        showImage(imageUri)
//
//        imageUri?.let { uri ->
//            binding.imagePreview.setImageURI(uri)
//        }
//
//        val filePath: String? = imageUri?.let { uri ->
//            if (uri.scheme == "file") {
//                uri.path
//            } else {
//                // Jika URI adalah content://, Anda mungkin perlu mengonversinya menjadi path file
//                // Misalnya, dengan bantuan metode getContentResolver().query()
//                // Tapi ini hanya contoh dan Anda perlu mengimplementasikannya sesuai kebutuhan
//                null
//            }
//        }

        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
        setupView()
        binding.btnUpload.setOnClickListener { uploadImage() }

        // Lanjutkan dengan implementasi logika pengiriman gambar ke server atau pemrosesan lainnya
        // ...
    }
    private fun showImage(uri: Uri?) {
        if (uri != null) {
            binding.imagePreview.setImageURI(uri)
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            Log.d("Image File", "showImage: ${imageFile.path}")
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}