package com.geeks.group.taxi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.geeks.R
import com.geeks.databinding.ActivityGroupListBinding
import com.geeks.group.AddItemActivity
import com.geeks.group.adapter.DeliveryListAdapter
import com.geeks.group.adapter.TaxiListAdapter
import com.geeks.group.delivery.DeliveryFeedActivity
import com.geeks.model.GetDeliveryResponse
import com.geeks.model.TaxiModel
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaxiListActivity : AppCompatActivity() {

    private var _binding: ActivityGroupListBinding?=null

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
        setContentView(R.layout.activity_group_list)

        _binding=ActivityGroupListBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.titleText.text="공동택시"

        binding.addItem.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        val view=binding.root
        setContentView(view)
    }

    private fun getTaxiList(){
        RetrofitBuilder.api.getTaxiList().enqueue(object :
            Callback<List<TaxiModel>> {
            override fun onResponse(

                call: Call<List<TaxiModel>>,
                response: Response<List<TaxiModel>>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑


                    binding.rvList.adapter= TaxiListAdapter(data).apply{
                        setItemClickListener(
                            object : TaxiListAdapter.ItemClickListener {
                                override fun onClick(view: View, position: Int) {
                                    val id=taxiList[position].id

                                    val intent = Intent(
                                        this@TaxiListActivity,
                                        TaxiFeedActivity::class.java)

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

            override fun onFailure(call: Call<List<TaxiModel>>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume(){
        getTaxiList()
        super.onResume()
    }
}