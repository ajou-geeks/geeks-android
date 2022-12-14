package com.geeks.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.geeks.R
import com.geeks.databinding.ActivityNoticeListBinding
import com.geeks.model.GetNoticeResponse

import com.geeks.notice.adapter.NoticeListAdapter
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeListActivity : AppCompatActivity() {
    private var _binding: ActivityNoticeListBinding? = null

    private val binding get() = _binding!!

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_list)

        _binding = ActivityNoticeListBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        getNoticeList()

        val view = binding.root
        setContentView(view)
    }

    private fun getNoticeList() {
        RetrofitBuilder.api.getNoticeList().enqueue(object :
            Callback<GetNoticeResponse> {
            override fun onResponse(

                call: Call<GetNoticeResponse>,
                response: Response<GetNoticeResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑


                    binding.rvList.adapter = NoticeListAdapter(data.elements)

                }
            }

            override fun onFailure(call: Call<GetNoticeResponse>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume() {
        getNoticeList()
        super.onResume()
    }
}