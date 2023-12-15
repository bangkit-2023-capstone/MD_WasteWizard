package com.example.wastewizard.ui.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.wastewizard.R
import com.example.wastewizard.ui.login.LoginActivity
import com.example.wastewizard.ui.signup.SignUp2
import org.w3c.dom.Text

class RegisterSignup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_signup)

        //Tombol Sign Up
        val btnSignEmail: Button = findViewById(R.id.btn_SignEmail)
          //btnSignEmail.setOnClickListener(this)
          btnSignEmail.setOnClickListener {
              Toast.makeText(this@RegisterSignup, "Button Email ditekan", Toast.LENGTH_SHORT).show()
              val moveIntent = Intent(this, SignUp2::class.java)
              startActivity(moveIntent)
        }

        //Text Login
        val txtUdahLogin: TextView = findViewById(R.id.txt_Udhlogin)
        txtUdahLogin.setOnClickListener {
            Toast.makeText(this@RegisterSignup, "Text Login ditekan", Toast.LENGTH_SHORT).show()
            val moveIntent = Intent(this, LoginActivity::class.java)
            startActivity(moveIntent)
        }
    }

    //memberikan aksi ketika tombol SignUp ditekan
//    override fun onClick(p0: View) {
//        if (p0 != null){
//            when(p0.id){
//                R.id.btn_SignEmail -> {
//                    val moveIntent = Intent(this, SignUp2::class.java)
//                    startActivity(moveIntent)
//                }
//            }
//        }
//    }
}