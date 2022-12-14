package com.geeks.group

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.util.Pair
import com.geeks.LoginActivity
import com.geeks.R
import com.geeks.databinding.ActivityAddItemBinding
import com.geeks.model.*
import com.geeks.retrofit.RetrofitBuilder
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class AddItemActivity : AppCompatActivity() {
    private var _binding: ActivityAddItemBinding?=null

    private val binding get() = _binding!!

    private var switching=0

    private var pickedDate: Long=Long.MIN_VALUE

    private var hour=0
    private var minute=0

    companion object {
        const val PERMISSION_REQUEST_CODE = 1001
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
        setContentView(R.layout.activity_add_item)

        _binding=ActivityAddItemBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        var filePath : String?=null
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            try {
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )

                val resolver: ContentResolver
                resolver = this.contentResolver

                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                var inputStream = resolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)

                filePath=getRealPathFromURI(it.data!!.data!!)

                Log.d("test",filePath!!)

                inputStream!!.close()
                inputStream = null
                bitmap?.let {
                    binding.thumbnail.setImageBitmap(bitmap)

                } ?: let {
                    Log.d("image", "bitmap null")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.addThumbnail.setOnClickListener {
            if(switching!=0) {
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                intent.type = "image/*"
                requestGalleryLauncher.launch(intent)
            }
            else{
                Toast.makeText(this, "????????? ???????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.taxi.setOnClickListener {
            setTaxi()
        }

        binding.product.setOnClickListener {
            setProduct()
        }

        binding.delivery.setOnClickListener {
            setDelivery()
        }

        binding.inputDate.setOnClickListener {
            if(switching==1) {
                val dateRangePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select dates")
                        .build()

                dateRangePicker.addOnPositiveButtonClickListener {
                    val dateFormat = SimpleDateFormat("yyyy??? MM??? dd???", Locale("ko", "kr"))

                    binding.inputDate.text = (dateFormat.format(it))
                    pickedDate = it
                }

                dateRangePicker.show(supportFragmentManager, "date")
            }
            else{
                val timePicker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(1)
                        .setMinute(0)
                        .setTitleText("?????? ?????? ??????")
                        .build()

                timePicker.addOnPositiveButtonClickListener {
                    hour = timePicker.hour
                    minute = timePicker.minute

                    binding.inputDate.text=hour.toString() + "?????? " + minute.toString() + "??? ??? ??????"
                }

                timePicker.show(supportFragmentManager, "time")
            }
        }

        binding.complete.setOnClickListener {
            when (switching) {
                0 -> {
                    createTaxi()
                }
                1 -> {
                    createProduct()
                }
                else -> {
                    createDelivery()
                }
            }
        }

        val view=binding.root
        setContentView(view)
    }

    private fun setTaxi(){
        binding.inputDate.text="???????????? ??????"

        binding.input3.visibility=View.VISIBLE
        binding.input4.visibility=View.INVISIBLE

        binding.input1.hint="?????? ??????"
        binding.input2.hint="?????? ??????"
        binding.input3.hint="?????? ??????"

        binding.input1.helperText="?????? ????????? ????????? ?????????"
        binding.input2.helperText="?????? ????????? ????????? ?????????"
        binding.input3.helperText="?????? ?????? ????????? ????????? ?????????"

        switching=0 //taxi view
    }

    private fun setProduct(){
        binding.inputDate.text="???????????? ??????"
        binding.input3.visibility=View.VISIBLE
        binding.input4.visibility=View.INVISIBLE

        binding.input1.hint="??????"
        binding.input2.hint="?????? ??????"
        binding.input3.hint="??????"


        binding.input1.helperText="?????? ????????? ????????? ?????????"
        binding.input2.helperText="?????? ????????? ????????? ?????????"
        binding.input3.helperText="????????? ????????? ????????? ?????????"

        switching=1
    }

    private fun setDelivery(){
        binding.inputDate.text="???????????? ??????"
        binding.input3.visibility=View.VISIBLE
        binding.input4.visibility=View.VISIBLE

        binding.input1.hint="??????"
        binding.input2.hint="?????? ??????"
        binding.input3.hint="?????? ?????? ??????"
        binding.input4.hint="?????? ?????? ??????"

        binding.input1.helperText="?????? ????????? ????????? ?????????"
        binding.input2.helperText="?????? ????????? ????????? ?????????"
        binding.input3.helperText="?????? ?????? ????????? ????????? ?????????"
        binding.input4.helperText="?????? ?????? ????????? ????????? ?????????"

        switching=2
    }

    private fun showDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("????????? ??????")
            .setMessage("?????? ????????? ????????? ?????????")
            .setPositiveButton("??????",
                DialogInterface.OnClickListener { dialog, id ->

                })
        // ?????????????????? ????????????
        builder.show()
    }

    private fun createDelivery(){
        if(binding.inputText3.text.toString()=="" || binding.inputText4.text.toString()==""){
            showDialog()
        }

        if(hour==0 && minute==0){
            showDialog()
        }

        val currentMillis = System.currentTimeMillis()

        val dateFormat=SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale("ko", "kr"))

        val name=binding.inputText1.text.toString()
        val destination=binding.inputText2.text.toString()
        val minAmount=binding.inputText3.text.toString().toInt()
        val amount=binding.inputText4.text.toString().toInt()
        val maxParticipant=binding.slider.value.toInt()
        val startTime=dateFormat.format(currentMillis).replace(" ", "T")

        val endTimeCal=currentMillis+hour*1000*60*60 + minute*1000*60

        val endTime=dateFormat.format(endTimeCal).replace(" ", "T")

        if(name=="" || destination==""){
            showDialog()
        }

        val request= DeliveryCreateRequest(
            name=name, type1 = "??????", minAmount=minAmount, amount=amount, startTime=startTime, endTime=endTime,
            maxParticipant=maxParticipant, destination=destination, thumbnailUrl = "https://geeks-new-bucket.s3.ap-northeast-2.amazonaws.com/image/aaa.jpeg"
        )

        Log.d("testlog", request.toString())
        RetrofitBuilder.api.createDelivery(request).enqueue(object :
            Callback<DeliveryCreateResponse> {
            override fun onResponse(
                call: Call<DeliveryCreateResponse>,
                response: Response<DeliveryCreateResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("testlog", response.body().toString())

                    var data = response.body()!!

                    Toast.makeText(this@AddItemActivity, "?????? ??????", Toast.LENGTH_SHORT).show()

                    finish()

                }
                else {
                    Log.d("fail", response.code().toString())
                }
            }

            override fun onFailure(call: Call<DeliveryCreateResponse>, t: Throwable) {
                Log.d("test", "??????$t")
            }

        })
    }

    private fun createProduct(){

        if(pickedDate==Long.MIN_VALUE || binding.inputText3.text.toString()==""){
            showDialog()
        }

        val currentMillis = System.currentTimeMillis()

        val dateFormat=SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale("ko", "kr"))

        val name=binding.inputText1.text.toString()
        val destination=binding.inputText2.text.toString()
        val price=binding.inputText3.text.toString().toInt()
        val maxParticipant=binding.slider.value.toInt()
        val startTime=dateFormat.format(currentMillis).replace(" ", "T")
        val endTime=dateFormat.format(pickedDate).replace(" ", "T")

        if(name=="" || destination==""){
            showDialog()
        }

        val request=ProductCreateRequest(
            name=name, type1 = "??????", price=price, startTime=startTime, endTime=endTime,
            maxParticipant=maxParticipant, destination=destination, thumbnailUrl = "https://geeks-new-bucket.s3.ap-northeast-2.amazonaws.com/image/aaa.jpeg"
        )

        Log.d("testlog", request.toString())
        RetrofitBuilder.api.createProduct(request).enqueue(object :
            Callback<ProductCreateResponse> {
            override fun onResponse(
                call: Call<ProductCreateResponse>,
                response: Response<ProductCreateResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("testlog", response.body().toString())

                    var data = response.body()!!

                    Toast.makeText(this@AddItemActivity, "?????? ??????", Toast.LENGTH_SHORT).show()

                    finish()

                }
                else {
                    Log.d("fail", response.code().toString())
                }
            }

            override fun onFailure(call: Call<ProductCreateResponse>, t: Throwable) {
                Log.d("test", "??????$t")
            }

        })
    }

    private fun createTaxi(){
        if(hour==0 && minute==0){
            showDialog()
        }

        val currentMillis = System.currentTimeMillis()

        val dateFormat=SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale("ko", "kr"))

        val source=binding.inputText1.text.toString()
        val destination=binding.inputText2.text.toString()
        val price=binding.inputText3.text.toString().toInt()
        val maxParticipant=binding.slider.value.toInt()
        val startTime=dateFormat.format(currentMillis).replace(" ", "T")

        val endTimeCal=currentMillis+hour*1000*60*60 + minute*1000*60

        val endTime=dateFormat.format(endTimeCal).replace(" ", "T")

        if(source=="" || destination==""){
            showDialog()
        }

        val request= TaxiCreateRequest(
            source = source, startTime=startTime, endTime=endTime, price = price,
            maxParticipant=maxParticipant, destination=destination,
        )

        Log.d("testlog", request.toString())
        RetrofitBuilder.api.createTaxi(request).enqueue(object :
            Callback<TaxiCreateResponse> {
            override fun onResponse(
                call: Call<TaxiCreateResponse>,
                response: Response<TaxiCreateResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("testlog", response.body().toString())

                    var data = response.body()!!

                    Toast.makeText(this@AddItemActivity, "?????? ??????", Toast.LENGTH_SHORT).show()

                    finish()

                }
                else {
                    Log.d("fail", response.code().toString())
                }
            }

            override fun onFailure(call: Call<TaxiCreateResponse>, t: Throwable) {
                Log.d("test", "??????$t")
            }

        })
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val resolver: ContentResolver
        resolver = this.contentResolver

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = resolver.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight &&
                halfWidth / inSampleSize >= reqWidth
            ) {
                inSampleSize *= 3
            }
        }
        return inSampleSize
    }

    private fun getRealPathFromURI(uri: Uri): String {
        var buildName = Build.MANUFACTURER
        if (buildName.equals("Xiaomi")) {
            return uri.path!!
        }
        var columnIndex = 0
        var proj = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = contentResolver.query(uri, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }

    private fun showDialogToGetImg(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("????????? ??????")
            .setMessage("????????? ????????? ?????????")
            .setPositiveButton("??????",
                DialogInterface.OnClickListener { dialog, id ->

                })
        // ?????????????????? ????????????
        builder.show()
    }
}