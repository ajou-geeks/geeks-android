package com.geeks.group.delivery

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
import com.geeks.group.adapter.ProductListAdapter
import com.geeks.group.product.ProductFeedActivity
import com.geeks.model.GetDeliveryResponse
import com.geeks.model.GetProductResponse
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryListActivity : AppCompatActivity() {
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

        _binding= ActivityGroupListBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.titleText.text="공동배달"


        binding.addItem.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        getDeliveryList()

        val view=binding.root
        setContentView(view)
    }

    private fun getDeliveryList(){
        RetrofitBuilder.api.getDeliveryList().enqueue(object :
            Callback<GetDeliveryResponse> {
            override fun onResponse(

                call: Call<GetDeliveryResponse>,
                response: Response<GetDeliveryResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑


                    binding.rvList.adapter= DeliveryListAdapter(data.elements).apply{
                        setItemClickListener(
                            object : DeliveryListAdapter.ItemClickListener {
                                override fun onClick(view: View, position: Int) {
                                    val id=deliveryList[position].id

                                    val intent = Intent(
                                        this@DeliveryListActivity,
                                        ProductFeedActivity::class.java)

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

            override fun onFailure(call: Call<GetDeliveryResponse>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume(){
        getDeliveryList()
        super.onResume()
    }
}