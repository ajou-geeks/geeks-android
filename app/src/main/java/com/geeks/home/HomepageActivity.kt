package com.geeks.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geeks.R
import com.geeks.databinding.ActivityHomepageBinding
import com.geeks.group.delivery.DeliveryListActivity
import com.geeks.group.product.ProductListActivity
import com.geeks.group.taxi.TaxiListActivity
import com.geeks.roommate.RoommateRegisterActivity
import com.geeks.roommate.RoommateSearchActivity
import com.google.android.material.button.MaterialButton

class HomepageActivity : AppCompatActivity() {

    private var _binding: ActivityHomepageBinding?=null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        _binding=ActivityHomepageBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.taxiButton.setOnClickListener {
            val intent = Intent(this, TaxiListActivity::class.java)
            startActivity(intent)
        }

        binding.productButton.setOnClickListener {
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
        }

        binding.deliveryButton.setOnClickListener {
            val intent = Intent(this, DeliveryListActivity::class.java)
            startActivity(intent)
        }

        binding.nameText.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

        binding.dormText.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RoommateRegisterActivity::class.java)
            startActivity(intent)
        }

        binding.searchButton.setOnClickListener {
            val intent = Intent(this, RoommateSearchActivity::class.java)
            startActivity(intent)
        }

        val view=binding.root
        setContentView(view)
    }
}