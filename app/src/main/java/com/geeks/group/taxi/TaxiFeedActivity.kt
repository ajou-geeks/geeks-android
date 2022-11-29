package com.geeks.group.taxi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.geeks.R
import com.geeks.databinding.ActivityFeedBinding
import com.geeks.group.AddItemActivity

class TaxiFeedActivity : AppCompatActivity() {

    private var _binding: ActivityFeedBinding?=null

    private val binding get() = _binding!!

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        setSupportActionBar(binding.toolbar)

        _binding=ActivityFeedBinding.inflate(layoutInflater)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.titleText.text="공동택시"

        val view=binding.root
        setContentView(view)
    }
}