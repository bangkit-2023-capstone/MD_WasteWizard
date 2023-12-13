package com.example.wastewizard

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Tombol get started
        val btnGetstarted: Button = findViewById(R.id.btn_getstarted)
        btnGetstarted.setOnClickListener(this)
    }

    //memberikan aksi ketika tombol get started ditekan
    override fun onClick(v: View?) {
        if (v != null){
            when(v.id){
                R.id.btn_getstarted -> {
                    val moveIntent = Intent(this, RegisterSignup::class.java)
                    startActivity(moveIntent)
                }
            }
        }
    }
}