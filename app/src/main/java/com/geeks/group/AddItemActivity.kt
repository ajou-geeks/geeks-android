package com.geeks.group

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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.geeks.R
import com.geeks.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {
    private var _binding: ActivityAddItemBinding?=null

    private val binding get() = _binding!!

    private var switching=0

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
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
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

        val view=binding.root
        setContentView(view)
    }

    private fun setTaxi(){
        binding.input3.visibility=View.INVISIBLE
        binding.input4.visibility=View.INVISIBLE

        binding.input1.helperText="출발 장소를 입력해 주세요"
        binding.input2.helperText="도착 장소를 입력해 주세요"

        switching=0 //taxi view
    }

    private fun setProduct(){
        binding.input3.visibility=View.VISIBLE
        binding.input4.visibility=View.INVISIBLE

        binding.input1.helperText="글의 제목을 입력해 주세요"
        binding.input2.helperText="수령 장소를 입력해 주세요"
        binding.input3.helperText="물품의 가격을 입력해 주세요"

        switching=1
    }

    private fun setDelivery(){
        binding.input3.visibility=View.VISIBLE
        binding.input4.visibility=View.VISIBLE

        binding.input1.helperText="글의 제목을 입력해 주세요"
        binding.input2.helperText="수령 장소를 입력해 주세요"
        binding.input3.helperText="최소 주문 금액을 입력해 주세요"
        binding.input4.helperText="현재 주문 금액을 입력해 주세요"

        switching=2
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
        builder.setTitle("업로드 실패")
            .setMessage("사진을 등록해 주세요")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->

                })
        // 다이얼로그를 띄워주기
        builder.show()
    }
}