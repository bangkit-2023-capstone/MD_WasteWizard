package com.example.wastewizard.ui.upload

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wastewizard.data.response.ModelResponse
import com.example.wastewizard.data.retrofit.APIModel
import com.example.wastewizard.data.retrofit.ApiService
import com.example.wastewizard.databinding.ActivityUploadBinding
import com.example.wastewizard.ui.klasifikasi.KlasifikasiActivity
import com.example.wastewizard.ui.klasifikasi.reduceFileImage
import com.example.wastewizard.ui.klasifikasi.uriToFile
import com.example.wastewizard.ui.result.ResultActivity
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var apiService: ApiService
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = APIModel.getApiModel()

        binding.btnBack.setOnClickListener{
            val intentBack = Intent(this@UploadActivity, KlasifikasiActivity::class.java)
            startActivity(intentBack)
        }

        binding.btnUpload.setOnClickListener {
            // Check if imageUri is not null
            if (imageUri != null) {
                // Resize and compress the image if needed
                val compressedImageFile = reduceAndSaveImage(imageUri!!)

                // Prepare the file part for uploading
                val filePart = prepareFilePart("file", compressedImageFile)

                // Make the API call
                val call = apiService.uploadImage(filePart)

                call.enqueue(object : Callback<ModelResponse> {
                    override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
                        // Handle the response here
                        if (response.isSuccessful) {
                            val modelResponse = response.body()
                            // Process the modelResponse

                            // Assuming result is a string, you can modify this based on the actual response
                            val result = Gson().toJson(modelResponse)

                            // Navigate to ResultActivity and pass the result
                            val intent = Intent(this@UploadActivity, ResultActivity::class.java)
                            intent.putExtra("result", result)
                            intent.putExtra("compressedImagePath", compressedImageFile.path)
                            startActivity(intent)

                        } else {
                            // Handle error
                        }
                    }

                    override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                        // Handle failure
                    }
                })
            }
        }

        // Retrieve imageUri from the intent
        imageUri = intent.getParcelableExtra("imageUri")

        // Display the image in the preview
        showImage(imageUri)
    }

    private fun prepareFilePart(partName: String, file: File): MultipartBody.Part {
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    private fun showImage(uri: Uri?) {
        if (uri != null) {
            binding.imagePreview.setImageURI(uri)
        }
    }

    private fun reduceAndSaveImage(uri: Uri): File {
        val originalFile = uriToFile(uri, this)

        // Check if the Android version is Q or higher
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            originalFile.reduceFileImage()
        } else {
            // If not on Q or higher, return the original file
            originalFile
        }
    }
}




