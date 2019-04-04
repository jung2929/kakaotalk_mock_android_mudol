package com.computer.inu.sqkakaotalk

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_myprofile.*
import org.jetbrains.annotations.Nullable
import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.content.Intent
import android.util.Base64
import java.io.ByteArrayOutputStream



class MyprofileActivity : AppCompatActivity() {



    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)
        tv_myprofile_background.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        idfinish.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.stay, R.anim.sliding_down)
        }

        iv_myprofile_mypicture.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 1)
        }

        if (SharedPreferenceController.getIMAGE(this).isNotEmpty()){
            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(this), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_myprofile_mypicture.setImageBitmap(decodedByte)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    val `in` = contentResolver.openInputStream(data!!.data!!)
                    val img = BitmapFactory.decodeStream(`in`)
                    getBase64String(img)
                    `in`!!.close()
                    // 이미지 표시
                    iv_myprofile_mypicture.setImageBitmap(img)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
      public fun getBase64String ( bitmap : Bitmap)
    {
        val byteArrayOutputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes = byteArrayOutputStream.toByteArray()
       SharedPreferenceController.setIMAGE(this,Base64.encodeToString(imageBytes, Base64.NO_WRAP))

    }





}

