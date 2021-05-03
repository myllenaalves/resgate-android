package com.example.resgatemeupet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun toLogin(view: View?) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun toCadastro(view: View?) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}