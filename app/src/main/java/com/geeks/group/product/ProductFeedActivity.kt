package com.geeks.group.product


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.geeks.R
import com.geeks.databinding.ActivityFeedBinding
import com.geeks.model.JoinModel
import com.geeks.model.ProductModel
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFeedActivity : AppCompatActivity() {
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

        binding.button.setOnClickListener {
            joinProduct()
        }

        getProductDetail()

        val view=binding.root
        setContentView(view)
    }

    private fun getProductDetail(){
        val id=getId()

        RetrofitBuilder.api.getProductDetail(id).enqueue(object :
            Callback<ProductModel> {
            override fun onResponse(

                call: Call<ProductModel>,
                response: Response<ProductModel>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑

                    presentData(data)

                }
            }

            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun joinProduct(){
        val id=getId()

        val request=JoinModel(id=id)

        RetrofitBuilder.api.joinProduct(request).enqueue(object :
            Callback<ProductModel> {
            override fun onResponse(

                call: Call<ProductModel>,
                response: Response<ProductModel>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑

                    presentData(data)

                    Toast.makeText(this@ProductFeedActivity,
                        "참여 완료", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun presentData(data: ProductModel){
        binding.titleText.text=data.name
        binding.text1.text=data.destination
        binding.text2.text="현재 인원 수 (${data.curParticipant} / ${data.maxParticipant})"
        binding.text3.text="마감 시간 - ${data.endTime
            .substring(0 until 10).replace("-",". ")}"

        binding.price1.text=data.price.toString() + " 원"

        val totalPrice=data.price*data.maxParticipant
        binding.price2.text="($totalPrice 원 / ${data.maxParticipant} 명)"
    }
}