package com.geeks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geeks.databinding.ActivityLoginBinding
import com.geeks.home.HomepageActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding?=null

    private val binding get()=_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        _binding=ActivityLoginBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val view=binding.root
        setContentView(view)
    }
}