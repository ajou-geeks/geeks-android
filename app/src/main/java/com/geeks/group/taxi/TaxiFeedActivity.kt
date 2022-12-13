package com.geeks.group.taxi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.geeks.R
import com.geeks.databinding.ActivityFeedBinding
import com.geeks.group.AddItemActivity
import com.geeks.model.DeliveryModel
import com.geeks.model.TaxiModel
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaxiFeedActivity : AppCompatActivity() {

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

        _binding=ActivityFeedBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.titleText.text="공동택시"

        getTaxiDetail()

        val view=binding.root
        setContentView(view)
    }

    private fun getTaxiDetail(){
        val id=getId()

        RetrofitBuilder.api.getTaxiDetail(id).enqueue(object :
            Callback<TaxiModel> {
            override fun onResponse(

                call: Call<TaxiModel>,
                response: Response<TaxiModel>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑

                    binding.titleText.text="도착: ${data.source}"
                    binding.text1.text="출발: ${data.destination}"
                    binding.text2.text="최대 인원 수: ${data.maxParticipant}"
                    binding.text3.text="마감 시간 - ${data.endTime
                        .substring(11 until 16)}"

                    binding.price1.text="예상 금액"
                    binding.price2.text=": " + data.price.toString() + " 원"

                }
            }

            override fun onFailure(call: Call<TaxiModel>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })
    }
}