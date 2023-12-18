package com.example.wastewizard.ui.signup

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wastewizard.R
import com.example.wastewizard.data.UserRepository
import com.example.wastewizard.databinding.ActivitySignUp2Binding
import com.example.wastewizard.di.Injection
import com.example.wastewizard.ui.ViewModelFactory
import com.example.wastewizard.ui.login.LoginActivity
import com.example.wastewizard.ui.login.LoginViewModel
import com.example.wastewizard.ui.welcome.MainViewModel

class SignUp2: AppCompatActivity() {

    private lateinit var binding: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = .inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        //Text Login
        val txtUdhlogin2: TextView = findViewById(R.id.txt_Udhlogin2)
        txtUdhlogin2.setOnClickListener {
            Toast.makeText(this@SignUp2, "Text Login ditekan", Toast.LENGTH_SHORT).show()
            val moveIntent = Intent(this, LoginActivity::class.java)
            startActivity(moveIntent)
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
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()

            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                setPositiveButton("Lanjut") { _, _ ->
                    finish()
                }
                create()
                show()
            }
        }
    }
}

