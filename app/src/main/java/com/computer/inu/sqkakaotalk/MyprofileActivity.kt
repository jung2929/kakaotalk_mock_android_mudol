package com.computer.inu.sqkakaotalk

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat
import android.support.v4.content.CursorLoader
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.Gravity
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.get.GetUserInfomationResponse
import com.computer.inu.sqkakaotalk.get.GetprofileResponse
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.NetworkService
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostLoginResponse
import com.computer.inu.sqkakaotalk.post.PostprofileResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.kakao.auth.StringSet.status
import kotlinx.android.synthetic.main.activity_friend_list_fragment.*
import kotlinx.android.synthetic.main.activity_myprofile.*
import org.jetbrains.anko.ctx
import org.jetbrains.annotations.Nullable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream



class MyprofileActivity : AppCompatActivity() {
    var profileimage : String? = null
    var Backgroundimage : String? = null
    var choice : Int = 0
var imageURI : String? = null
    var status : String? =null
    val REQUEST_CODE_SELECT_IMAGE : Int = 1004
    val   My_READ_STORAGE_REQUEST_CODE = 7777
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprofile)

        if (SharedPreferenceController.getKaKaOAuthorization(this).isNotEmpty()) { // 카카오 로그인일때 정보받아오기
            getKAKAOUserInfoPost()
        }else if(SharedPreferenceController.getSQAuthorization(this).isNotEmpty()){
            getMyProfile()
            getMyprofilePost2()
        }

        tv_myprofile_background.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        idfinish.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.stay, R.anim.sliding_down)
        }
        iv_backgound_modify_button.setOnClickListener {
            choice=0
            requestReadExternalStoragePermission()
        }
        iv_myprofile_mypicture.setOnClickListener {
            choice=1
           requestReadExternalStoragePermission()
        }

        if (SharedPreferenceController.getIMAGE(this).isNotEmpty()){
            val decodedString = Base64.decode(SharedPreferenceController.getIMAGE(this), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_myprofile_mypicture.setImageBitmap(decodedByte)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//REQUEST_CODE_SELECT_IMAGE를 통해 앨범에서 보낸 요청에 대한 Callback인지를 체크!!!
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
//data.data에는 앨범에서 선택한 사진의 Uri가 들어있습니다!! 그러니까 제대로 선택됐는지 null인지 아닌지를 체크!!!
                if(data != null){
                    val selectedImageUri : Uri = data.data


                    imageURI = getRealPathFromURI(selectedImageUri)
                    //selectedImageUri URI
                    if(choice==1){ //프로필
                   Glide.with(this).load(selectedImageUri).into(iv_myprofile_mypicture)
                    }
                    else if (choice==0){ //배경화면
                        Glide.with(this).load(selectedImageUri).into(tv_myprofile_background)
                    }
                    UpdateMyprofilePost(selectedImageUri)}
                }

        }
    }

     fun requestReadExternalStoragePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    My_READ_STORAGE_REQUEST_CODE
                )
            }
        } else {
            showAlbum()
        }
    }
   override  fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//onActivityResult와 같은 개념입니다. requestCode로 어떤 권한에 대한 callback인지를 체크합니다.
         if (requestCode == My_READ_STORAGE_REQUEST_CODE) {
//결과에 대해 허용을 눌렀는지 체크하는 조건문이구요!
             if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//이곳은 외부저장소 접근을 허용했을 때에 대한 로직을 쓰시면됩니다. 우리는 앨범을 여는 메소드를 호출해주면되겠죠?
                 showAlbum()
             } else {
//이곳은 외부저장소 접근 거부를 했을때에 대한 로직을 넣어주시면 됩니다.
                 finish()
             }
         }
     }
        fun getRealPathFromURI(content : Uri) : String {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val loader : CursorLoader = CursorLoader(this, content, proj, null, null, null)
            val cursor : Cursor = loader.loadInBackground()!!
            val column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val result = cursor.getString(column_idx)
            cursor.close()
            return result
        }
    fun UpdateMyprofilePost(imageURI :Uri){
        var jsonObject = JSONObject()
        if(choice==1){
            jsonObject.put("Prof_img",imageURI.toString())
            jsonObject.put("Back_img", Backgroundimage)
            jsonObject.put("Status", status)
        }else if (choice==0){
            jsonObject.put("Prof_img",profileimage)
            jsonObject.put("Back_img", imageURI.toString())
            jsonObject.put("Status", status)
        }

//Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var UpdateProfileResponse: Call<PostprofileResponse> = SqnetworkService.postprofileResponse("application/json",SharedPreferenceController.getSQAuthorization(this),gsonObject)
        UpdateProfileResponse.enqueue(object : Callback<PostprofileResponse> {
            override fun onResponse(call: Call<PostprofileResponse>?, response: Response<PostprofileResponse>?) {
                Log.v("TAG", "프로필 업데이트")
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공") {


                    }
                }

            }
            override fun onFailure(call: Call<PostprofileResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }

    fun showAlbum(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
    }
    fun getMyprofilePost2(){
        var jsonObject = JSONObject()
        jsonObject.put("Email",SharedPreferenceController.getEmail(ctx))
        jsonObject.put("Pw", SharedPreferenceController.getPW(ctx))

//Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var postLoginResponse: Call<PostLoginResponse> = SqnetworkService.postLoginResponse("application/json",gsonObject)
        postLoginResponse.enqueue(object : Callback<PostLoginResponse> {
            override fun onResponse(call: Call<PostLoginResponse>?, response: Response<PostLoginResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공") {

                        tv_myprofile_myname.setText(response.body()!!.result[0].Name.toString())
                        tv_myprofile_email.setText(response.body()!!.result[0].Email.toString())

                    }
                }

            }
            override fun onFailure(call: Call<PostLoginResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }

    fun getMyProfile(){
        var getProfileResponse: Call<GetprofileResponse> = SqnetworkService.getprofileResponse("application/json",SharedPreferenceController.getSQAuthorization(ctx))
        getProfileResponse.enqueue(object : Callback<GetprofileResponse> {
            override fun onResponse(call: Call<GetprofileResponse>?, response: Response<GetprofileResponse>?) {
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공"){
                        profileimage=response.body()!!.result.Prof_img.toString()
                        Backgroundimage=response.body()!!.result.Back_img.toString()
                        status = response.body()!!.result.Status.toString()
                        Glide.with(ctx).load(response.body()!!.result.Prof_img.toString()).into(iv_myprofile_mypicture)
                        Glide.with(ctx).load(response.body()!!.result.Back_img.toString()).into(tv_myprofile_background)
                        tv_Myprofile_status.setText(response.body()!!.result.Status.toString())

                    }
                }
                else{
                    Log.v("TAG", "실패")
                }
            }
            override fun onFailure(call: Call<GetprofileResponse>?, t: Throwable?) {
                Log.v("TAG", "실패 = " +t.toString())
            }
        })
    }

fun getMyprofilePost(){
        var jsonObject = JSONObject()
        jsonObject.put("Email",SharedPreferenceController.getEmail(ctx))
        jsonObject.put("Pw", SharedPreferenceController.getPW(ctx))

//Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var postLoginResponse: Call<PostLoginResponse> = SqnetworkService.postLoginResponse("application/json",gsonObject)
        postLoginResponse.enqueue(object : Callback<PostLoginResponse> {
            override fun onResponse(call: Call<PostLoginResponse>?, response: Response<PostLoginResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공") {

                                tv_friend_myname.setText(response.body()!!.result[0].Name.toString())

                    }
                }

            }
            override fun onFailure(call: Call<PostLoginResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
    fun getKAKAOUserInfoPost(){
        var getUserInfomationResponse: Call<GetUserInfomationResponse> = networkService.getUserInfomationResponse("Bearer "+SharedPreferenceController.getKaKaOAuthorization(this))
        getUserInfomationResponse.enqueue(object : Callback<GetUserInfomationResponse> {
            override fun onResponse(call: Call<GetUserInfomationResponse>?, response: Response<GetUserInfomationResponse>?) {
                Log.v("TAG", "보드 서버 통신 연결")
                if (response!!.isSuccessful) {
                    tv_myprofile_myname.text = response.body()!!.properties.nickname

                    Glide.with(ctx).load(response.body()!!.properties.profile_image.toString()).into(tv_myprofile_background)
                    Glide.with(ctx).load(response.body()!!.properties.profile_image.toString()).into(iv_myprofile_mypicture)
                }
                else{
                    Log.v("TAG", "마이페이지 서버 값 전달 실패")
                }
            }
            override fun onFailure(call: Call<GetUserInfomationResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }

}

