package com.geeks.group.delivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.geeks.R
import com.geeks.databinding.ActivityFeedBinding
import com.geeks.model.DeliveryModel
import com.geeks.model.ProductModel
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryFeedActivity : AppCompatActivity() {
    private var _binding: ActivityFeedBinding?=null

    private val binding get() = _binding!!

    private fun getId(): Int {
        return intent.getIntExtra("id", 0)
    }

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

        _binding= ActivityFeedBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.titleText.text="공동택시"

        getDeliveryDetail()

        val view=binding.root
        setContentView(view)
    }

    private fun getDeliveryDetail(){
        val id=getId()

        RetrofitBuilder.api.getDeliveryDetail(id).enqueue(object :
            Callback<DeliveryModel> {
            override fun onResponse(

                call: Call<DeliveryModel>,
                response: Response<DeliveryModel>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑

                    binding.titleText.text=data.name
                    binding.text1.text=data.destination
                    binding.text2.text="현재 인원 수 (${data.curParticipant} / 무제한)"
                    binding.text3.text="마감 시간 - ${data.endTime
                        .substring(11 until 16)}"

                    binding.price1.text="최소주문금액"
                    binding.price2.text=": " + data.minAmount.toString() + " 원"

                }
            }

            override fun onFailure(call: Call<DeliveryModel>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })
    }
}