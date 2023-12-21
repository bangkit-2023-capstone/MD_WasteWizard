package com.example.wastewizard.ui.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.wastewizard.R
import com.example.wastewizard.ResultState
import com.example.wastewizard.databinding.ActivitySignUp2Binding
import com.example.wastewizard.ui.ViewModelFactory
import com.example.wastewizard.ui.login.LoginActivity
import com.example.wastewizard.ui.main.DashboardActivity
import com.example.wastewizard.ui.viewmodel.LoginViewModel
import com.example.wastewizard.ui.viewmodel.SignUp2ViewModel

class SignUp2: AppCompatActivity() {
    private val viewModel by viewModels<SignUp2ViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySignUp2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUp2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        //Text Login
        val txtLogin: TextView = findViewById(R.id.txt_login)
        txtLogin.setOnClickListener {
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
            val name = binding.usernameEditText.text.toString()
            val password=binding.passwordEditText.text.toString()
            viewModel.userRegister(name, email, password).observe(this){ result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
//                            showLoading(true)
                            Log.d("register", "$name & $email & $password")
                        }
                        is ResultState.Success -> {
//                            showToast(result.data.message)
//                            showLoading(false)
                            Log.d("Register Sukses", "$name & $email & $password")
//                            setupAction(result.data.loginResult)
                            AlertDialog.Builder(this).apply {
                                setTitle("Yeah!")
                                setMessage("Anda berhasil register. Silahkan Login!")
                                setPositiveButton("Lanjut Login") { _, _ ->
                                    val intent = Intent(context, LoginActivity::class.java)
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
                            Log.d("Register Error", "$name & $email & $password")
                        }
                    }
                }
            }
            Log.d("Register", "$name & $email & $password")
        }
    }
}

