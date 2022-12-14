package com.geeks.roommate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.geeks.R
import com.geeks.databinding.ActivityRoommateSearchBinding
import com.geeks.group.adapter.RoommateListAdapter
import com.geeks.group.delivery.DeliveryFeedActivity

import com.geeks.model.SearchRoommateResponse
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoommateSearchActivity : AppCompatActivity() {

    private var _binding: ActivityRoommateSearchBinding?=null

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
        setContentView(R.layout.activity_roommate_search)

        _binding=ActivityRoommateSearchBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val view=binding.root
        setContentView(view)
    }

    private fun searchRoommate(){
        RetrofitBuilder.api.searchRoommate().enqueue(object :
            Callback<SearchRoommateResponse> {
            override fun onResponse(

                call: Call<SearchRoommateResponse>,
                response: Response<SearchRoommateResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑


                    binding.rvList.adapter= RoommateListAdapter(data.elements).apply{
                        setItemClickListener(
                            object : RoommateListAdapter.ItemClickListener {
                                override fun onClick(view: View, position: Int) {
                                    val id=roommateList[position].id

                                    val intent = Intent(
                                        this@RoommateSearchActivity,
                                        RoommateProfileActivity::class.java)

                                    intent.apply {
                                        this.putExtra("id",id) // 데이터 넣기
                                    }
                                    startActivity(intent)

                                    onStop()
                                }
                            })
                    }

                }
            }

            override fun onFailure(call: Call<SearchRoommateResponse>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume(){
        searchRoommate()
        super.onResume()
    }
}