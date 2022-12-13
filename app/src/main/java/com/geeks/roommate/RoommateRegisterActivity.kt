package com.geeks.roommate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.geeks.R
import com.geeks.databinding.ActivityRoommateRegisterBinding
import com.geeks.model.CreateRoommateRequest
import com.geeks.model.CreateRoommateResponse
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoommateRegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRoommateRegisterBinding?=null

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
        setContentView(R.layout.activity_roommate_register)

        _binding=ActivityRoommateRegisterBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.complete.setOnClickListener {
            createRoommate()
        }

        val view=binding.root
        setContentView(view)
    }

    private fun createRoommate(){
        val bio=binding.bioInput.text.toString()
        val patternDetail=binding.patternInput.text.toString()
        var pattern: String?=null

        var characterType= mutableListOf<String>()

        if(binding.extrovert.isChecked){
            characterType.add("외향적인")
        }

        if(binding.introvert.isChecked){
            characterType.add("내향적인")
        }

        if(binding.funny.isChecked){
            characterType.add("재미있는")
        }

        if(binding.easygoing.isChecked){
            characterType.add("느긋한")
        }

        if(binding.hardworking.isChecked){
            characterType.add("부지런한")
        }

        if(binding.playful.isChecked){
            characterType.add("쾌활한")
        }

        if(binding.meticulous.isChecked){
            characterType.add("꼼꼼한")
        }

        if(binding.unrestrained.isChecked){
            characterType.add("거리낌없는")
        }

        if(binding.energetic.isChecked){
            characterType.add("에너지있는")
        }

        if(binding.funLoving.isChecked){
            characterType.add("잘노는")
        }

        if(binding.shy.isChecked){
            characterType.add("수줍은")
        }

        if(binding.talkative.isChecked){
            characterType.add("수다스러운")
        }

        if(binding.messy.isChecked){
            characterType.add("칠칠맞은")
        }

        if(binding.sensitive.isChecked){
            characterType.add("예민한")
        }

        if(binding.bear.isChecked){
            pattern="곰형"
        }

        if(binding.lion.isChecked){
            pattern="사자형"
        }

        if(binding.wolf.isChecked){
            pattern="늑대형"
        }

        if(binding.dolphin.isChecked){
            pattern="돌고래형"
        }

        val request= CreateRoommateRequest(
            bio =bio, patternDetail =patternDetail, pattern = pattern!!, characterType = characterType
        )

        Log.d("testlog", request.toString())
        RetrofitBuilder.api.createRoommateProfile(request).enqueue(object :
            Callback<CreateRoommateResponse> {
            override fun onResponse(
                call: Call<CreateRoommateResponse>,
                response: Response<CreateRoommateResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("testlog", response.body().toString())

                    var data = response.body()!!

                    Toast.makeText(this@RoommateRegisterActivity,
                        "등록 완료", Toast.LENGTH_SHORT).show()

                    finish()

                }
                else {
                    Log.d("fail", response.code().toString())
                }
            }

            override fun onFailure(call: Call<CreateRoommateResponse>, t: Throwable) {
                Log.d("test", "실패$t")
            }

        })
    }
}