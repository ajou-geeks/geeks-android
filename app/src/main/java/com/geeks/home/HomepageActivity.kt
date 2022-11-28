package com.geeks.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geeks.R
import com.google.android.material.button.MaterialButton

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val chatButton=findViewById<MaterialButton>(R.id.chatButton)

        chatButton.bringToFront()
    }
}