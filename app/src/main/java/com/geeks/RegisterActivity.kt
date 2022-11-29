package com.geeks

import android.annotation.SuppressLint
import android.content.*
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.geeks.databinding.ActivityRegisterBinding
import com.geeks.model.RegisterResponse
import com.geeks.retrofit.RetrofitBuilder
import retrofit2.Callback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.util.Collections.min
import kotlin.math.min


class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding?=null

    private val binding get() = _binding!!

    private var filePath : String?=null

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
        setContentView(R.layout.activity_register)

        _binding=ActivityRegisterBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        var fileName : String?=null
        val requestFinderLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            try {

                val resolver: ContentResolver
                resolver = this.contentResolver

                val option = BitmapFactory.Options()

                var inputStream = resolver.openInputStream(it.data!!.data!!)

                filePath=getFilePath(this@RegisterActivity, it.data!!.data!!)
                fileName=getFileName(it.data!!.data!!)

                Log.d("testlog",filePath!!)
                Log.d("testlog", fileName!!)

                binding.upload.text=fileName!! + " 업로드 완료"

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.upload.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_OPEN_DOCUMENT
            )
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "application/pdf"
            requestFinderLauncher.launch(intent)
        }

        binding.registerButton.setOnClickListener {
            register()
        }

        val view=binding.root
        setContentView(view)
    }

    private fun register(){
        if(filePath==null){
            showDialogToGetFile()
        }
        else {
            val inputEmail = binding.inputEmail.text.toString().replace("'", """\'""")
            val inputPassword = binding.inputPassword.text.toString().replace("'", """\'""")
            val inputPasswordConfirm = binding.inputPasswordConfirm.text.toString().replace("'", """\'""")

            if(inputPassword!=inputPasswordConfirm){
                binding.passwordText.isErrorEnabled=true
                binding.passwordText.error="비밀번호가 일치하지 않습니다."
                binding.passwordConfirmText.isErrorEnabled=true
                binding.passwordConfirmText.error="비밀번호가 일치하지 않습니다."
                return
            }

            val emailRequest=RequestBody.create(MediaType.parse("text/plain"), inputEmail)
            val passwordRequest=RequestBody.create(MediaType.parse("text/plain"), inputPassword)

            var file = File(filePath)
            var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            var body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            RetrofitBuilder.api.register(
                emailRequest, passwordRequest, body
            ).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "등록되었습니다.", Toast.LENGTH_SHORT).show()

                        finish()
                    } else {
                        when (response.code()) {
                            400 -> Log.d("testlog", response.code().toString())
                        }
                        Log.d("testlog", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "실패했습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("testlog", "실패$t")
                    finish()
                }
            })
        }
    }

    private fun showDialogToGetFile(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("회원가입 실패")
            .setMessage("입사확인서를 업로드해 주세요")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->

                })
        // 다이얼로그를 띄워주기
        builder.show()
    }


    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.lastPathSegment
        }
        return result
    }

    fun getFilePath(context: Context, contentUri: Uri): String? {
        try {
            val filePathColumn = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
            )

            val returnCursor = contentUri.let { context.contentResolver.query(it, filePathColumn, null, null, null) }

            if (returnCursor != null) {

                returnCursor.moveToFirst()
                val nameIndex = returnCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                val name = returnCursor.getString(nameIndex)
                val file = File(context.cacheDir, name)
                val inputStream = context.contentResolver.openInputStream(contentUri)
                val outputStream = FileOutputStream(file)
                var read: Int
                val maxBufferSize = 1 * 1024 * 1024
                val bytesAvailable = inputStream!!.available()

                val bufferSize = min(bytesAvailable, maxBufferSize)
                val buffers = ByteArray(bufferSize)

                while (inputStream.read(buffers).also { read = it } != -1) {
                    outputStream.write(buffers, 0, read)
                }

                inputStream.close()
                outputStream.close()
                return file.absolutePath
            }
            else
            {
                Log.d("","returnCursor is null")
                return null
            }
        }
        catch (e: Exception) {
            Log.d("","exception caught at getFilePath(): $e")
            return null
        }
    }
}