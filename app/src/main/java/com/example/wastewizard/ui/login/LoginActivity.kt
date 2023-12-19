package com.example.wastewizard.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.wastewizard.R
import com.example.wastewizard.ResultState
import com.example.wastewizard.data.pref.UserModel
import com.example.wastewizard.databinding.ActivityLoginBinding
import com.example.wastewizard.ui.ViewModelFactory
import com.example.wastewizard.ui.main.DashboardActivity
import com.example.wastewizard.ui.signup.SignUp2
import com.example.wastewizard.ui.viewmodel.LoginViewModel
import com.example.wastewizard.ui.welcome.MainActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()



        //Text Sign Up
        val txtSignUp: TextView = findViewById(R.id.txt_signup)
        txtSignUp.setOnClickListener {
            Toast.makeText(this@LoginActivity, "Text Sign Up ditekan", Toast.LENGTH_SHORT).show()
            Log.d("login", "Login Berhasuk")
            val moveIntent = Intent(this, SignUp2::class.java)
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
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password=binding.passwordEditText.text.toString()
//            viewModel.saveSession(UserModel(email, "sample_token"))
            viewModel.postLogin(email, password).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
//                            showLoading(true)
                            Log.d("Login", "$email & $password")
                        }
                        is ResultState.Success -> {
//                            showToast(result.data.message)
//                            showLoading(false)
                            Log.d("Login Sukses", "$email & $password")
//                            setupAction(result.data.loginResult)
                            AlertDialog.Builder(this).apply {
                                setTitle("Yeah!")
                                setMessage("Anda berhasil login. Mari kita atasi sampah bersama!")
                                setPositiveButton("Lanjut") { _, _ ->
                                    val intent = Intent(context, DashboardActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                            }
                        }
                        is ResultState.Error -> {
//                            showToast(result.error)
//                            wrongPassword()
//                            showLoading(false)
                            Log.d("Login Error", "$email & $password")
                        }
                    }
                }
            }
        }
    }
}