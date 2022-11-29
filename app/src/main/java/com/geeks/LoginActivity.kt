package com.geeks

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.geeks.databinding.ActivityLoginBinding
import com.geeks.home.HomepageActivity
import com.geeks.model.LoginRequest
import com.geeks.model.LoginResponse
import com.geeks.retrofit.RetrofitBuilder
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding?=null

    private val binding get()=_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        _binding=ActivityLoginBinding.inflate(layoutInflater)

        binding.loginButton.setOnClickListener {

            val id=binding.inputEmail.text.toString()
            val pw=binding.inputPassword.text.toString()

            val loginData= LoginRequest(email=id, password = pw)

            login(loginData)
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val view=binding.root
        setContentView(view)
    }

    private fun login(loginRequest: LoginRequest){
        RetrofitBuilder.api.login(loginRequest).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("testlog", response.body().toString())

                    var data = response.body()!!

                    if(data.jwt.token!=null){
                        val token=data.jwt.token
                        GlobalApplication.prefs.setString("token", token!!)
                        success() //로그인 성공
                    }
                }
                else {
                    Log.d("fail", response.code().toString())

                    failed() //로그인 실패
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("test", "실패$t")
            }

        })
    }

    private fun success(){
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }

    private fun failed(){
        binding.idText.isErrorEnabled=true
        binding.idText.error=" "

        binding.passwordText.isErrorEnabled=true
        binding.passwordText.error="아이디 또는 비밀번호를 확인해 주세요"
    }

    class GlobalApplication : Application() {
        companion object{
            lateinit var prefs : PreferenceUtil
        }
        override fun onCreate() {
            prefs= PreferenceUtil(applicationContext)
            super.onCreate()
        }
    }

    class PreferenceUtil(context: Context)
    {
        private val prefs: SharedPreferences =
            context.getSharedPreferences("tokens", Context.MODE_PRIVATE)

        fun getString(key: String, defValue: String): String
        {
            return prefs.getString(key, defValue).toString()
        }

        fun setString(key: String, str: String)
        {
            prefs.edit().putString(key, str).apply()
        }

        fun removeString(key: String)
        {
            prefs.edit().remove(key).commit()
        }
    }
}