package com.geeks.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.geeks.R
import com.geeks.databinding.ActivityNoteListBinding
import com.geeks.group.product.ProductFeedActivity
import com.geeks.model.GetNoteResponse
import com.geeks.note.adapter.NoteListAdapter
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteListActivity : AppCompatActivity() {

    private var _binding: ActivityNoteListBinding?=null

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
        setContentView(R.layout.activity_note_list)

        _binding=ActivityNoteListBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        getNoteList()

        val view=binding.root
        setContentView(view)
    }

    private fun getNoteList(){
        RetrofitBuilder.api.getNoteList().enqueue(object :
            Callback<GetNoteResponse> {
            override fun onResponse(

                call: Call<GetNoteResponse>,
                response: Response<GetNoteResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body()!! // GsonConverter를 사용해 데이터매핑


                    binding.rvList.adapter= NoteListAdapter(data.elements).apply{
                        setItemClickListener(
                            object : NoteListAdapter.ItemClickListener {
                                override fun onClick(view: View, position: Int) {
                                    val id=noteList[position].id
                                    val receiver=noteList[position].otherInfo.id

                                    val intent = Intent(
                                        this@NoteListActivity,
                                        NoteRoomActivity::class.java)

                                    intent.apply {
                                        this.putExtra("id",id) // 데이터 넣기
                                        this.putExtra("receiver", receiver)
                                    }
                                    startActivity(intent)

                                    onStop()
                                }
                            })
                    }

                }
            }

            override fun onFailure(call: Call<GetNoteResponse>, t: Throwable) {
                Log.d("test", "실패$t")
                //Toast.makeText(this@GoodsInfo, "업로드 실패 ..", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume(){
        getNoteList()
        super.onResume()
    }
}