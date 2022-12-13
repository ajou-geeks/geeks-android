package com.geeks.roommate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geeks.R
import com.geeks.databinding.ActivityRoommateSearchBinding

class RoommateSearchActivity : AppCompatActivity() {

    private var _binding: ActivityRoommateSearchBinding?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roommate_search)
    }
}