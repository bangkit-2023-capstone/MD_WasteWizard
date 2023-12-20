package com.example.wastewizard.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.wastewizard.databinding.ActivityDashboardBinding
import com.example.wastewizard.ui.ViewModelFactory
import com.example.wastewizard.ui.klasifikasi.KlasifikasiActivity
import com.example.wastewizard.ui.pickup.PickUpActivity
import com.example.wastewizard.ui.profile.ProfileActivity
import com.example.wastewizard.ui.viewmodel.DashboardViewModel
import com.example.wastewizard.ui.welcome.RegisterSignup

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

            viewModel.getSession().observe(this) { user ->
                if (!user.isLogin) {
                    startActivity(Intent(this, RegisterSignup::class.java))
                    finish()
                }else{
                }
            }

        binding.klasifikasi.setOnClickListener {
        val intent_klasifikasi = Intent(this@DashboardActivity, KlasifikasiActivity::class.java)
        startActivity(intent_klasifikasi)
        }

        binding.pickup.setOnClickListener {
            val intent_pickup = Intent(this@DashboardActivity, PickUpActivity::class.java)
            startActivity(intent_pickup)
        }

        binding.profile.setOnClickListener{
            val intent_profile = Intent(this@DashboardActivity, ProfileActivity::class.java)
            startActivity(intent_profile)
        }
    }
}