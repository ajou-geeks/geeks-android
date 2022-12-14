package com.geeks.note

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.geeks.R
import com.geeks.databinding.ActivityNoteRoomBinding
import com.geeks.model.GetNoteRoomResponse
import com.geeks.model.SendNoteModel
import com.geeks.note.adapter.NoteRoomAdapter
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteRoomActivity : AppCompatActivity() {
    private var _binding: ActivityNoteRoomBinding?=null

    private val binding get() = _binding!!

    private fun getId(): Int {
        return intent.getIntExtra("id", 0)
    }

    private fun getReceiver(): Int {
        return intent.getIntExtra("receiver", 0)
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
        setContentView(R.layout.activity_note_room)

        _binding= ActivityNoteRoomBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        binding.input.setEndIconOnClickListener {
            sendNote()
        }

        getNoteRoom()

        val view=binding.root
        setContentView(view)
    }

    private fun getNoteRoom(){
        val id=getId()

        Log.d("testlog", id.toString())

        RetrofitBuilder.api.getNoteRoom(id.toString()).enqueue(object :
            Callback<GetNoteRoomResponse> {
            override fun onResponse(

                call: Call<GetNoteRoomResponse>,
                response: Response<GetNoteRoomResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("testlog", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑

                    binding.rvList.adapter= NoteRoomAdapter(data.elements)
                }
            }

            override fun onFailure(call: Call<GetNoteRoomResponse>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun sendNote(){
        val receiver=getReceiver()
        val content=binding.inputText.text.toString()

        val request=SendNoteModel(receiverId = receiver, content = content)

        RetrofitBuilder.api.sendNote(request).enqueue(object :
            Callback<GetNoteRoomResponse> {
            override fun onResponse(

                call: Call<GetNoteRoomResponse>,
                response: Response<GetNoteRoomResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑

                    binding.rvList.adapter= NoteRoomAdapter(data.elements)
                }
            }

            override fun onFailure(call: Call<GetNoteRoomResponse>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onResume(){
        getNoteRoom()
        super.onResume()
    }
}