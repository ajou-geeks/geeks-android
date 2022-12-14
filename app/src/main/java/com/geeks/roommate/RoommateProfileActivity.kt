package com.geeks.roommate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.geeks.R
import com.geeks.databinding.ActivityRoommateProfileBinding
import com.geeks.model.GetNoteRoomResponse
import com.geeks.model.RoommateModel
import com.geeks.model.SendNoteModel
import com.geeks.note.adapter.NoteRoomAdapter
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoommateProfileActivity : AppCompatActivity() {
    private var _binding: ActivityRoommateProfileBinding? = null

    private val binding get() = _binding!!

    private fun getId(): Int {
        return intent.getIntExtra("id", 0)
    }

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
        setContentView(R.layout.activity_roommate_profile)

        _binding = ActivityRoommateProfileBinding.inflate(layoutInflater)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.input.setEndIconOnClickListener {
            sendNote()
        }

        getProfile()

        val view = binding.root
        setContentView(view)
    }

    private fun getProfile(){
        val id=getId()

        RetrofitBuilder.api.getRoommateProfile(id).enqueue(object :
            Callback<RoommateModel> {
            override fun onResponse(

                call: Call<RoommateModel>,
                response: Response<RoommateModel>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑

                    binding.userName.text=data.name
                    binding.introText.text=data.bio
                    binding.userEmail.text=data.email
                }
            }

            override fun onFailure(call: Call<RoommateModel>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun sendNote(){
        val receiver=getId()
        val content=binding.inputText.text.toString()

        val request= SendNoteModel(receiverId = receiver, content = content)

        RetrofitBuilder.api.sendNote(request).enqueue(object :
            Callback<GetNoteRoomResponse> {
            override fun onResponse(

                call: Call<GetNoteRoomResponse>,
                response: Response<GetNoteRoomResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑
                    Toast.makeText(this@RoommateProfileActivity, "쪽지가 전송되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetNoteRoomResponse>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })

    }
}