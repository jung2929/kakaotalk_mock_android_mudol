package com.computer.inu.sqkakaotalk

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.CursorLoader
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import com.computer.inu.sqkakaotalk.Adapter.ChatRecyclerViewAdapter
import com.computer.inu.sqkakaotalk.Data.ChatRoomData
import com.computer.inu.sqkakaotalk.Main.MainActivity
import com.computer.inu.sqkakaotalk.network.ApplicationController
import com.computer.inu.sqkakaotalk.network.SqNetworkService
import com.computer.inu.sqkakaotalk.post.PostChatResponse
import com.computer.inu.sqkakaotalk.post.PostLoginResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_in_message.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_myprofile.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InMessageActivity : AppCompatActivity() {
    val REQUEST_CODE_SELECT_IMAGE : Int = 1004
    val   My_READ_STORAGE_REQUEST_CODE = 7777
    var imageURI : String? = null
    var selectedImageUri2 : Uri? =null
    val SqnetworkService: SqNetworkService by lazy {
        ApplicationController.instance.SqnetworkService
    }
    lateinit var ChatMessageRecyclerView :ChatRecyclerViewAdapter
    val dataList : ArrayList<ChatRoomData> by lazy {
        ArrayList<ChatRoomData>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_in_message)

        btn_inmessage_picture.setOnClickListener {
            requestReadExternalStoragePermission()
            //사진 보여주기
        }

        if(SharedPreferenceController.getSQAuthorization(this).isNotEmpty()){
        tv_inmessage_name.setText(intent.getStringExtra("name"))
            ChatMessageRecyclerView = ChatRecyclerViewAdapter(this,dataList)
            rl_message_chat.adapter=ChatMessageRecyclerView
            rl_message_chat.layoutManager=LinearLayoutManager(this)
            edt_message.setOnEditorActionListener({ textView, action, event ->
                var handled = false
                if (action == EditorInfo.IME_ACTION_DONE) {
                    dataList.add(ChatRoomData(edt_message.text.toString(),"",true))
             chattingPost()
                }
                handled
            })
        }
    }

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
        return super.onCreateView(name, context, attrs)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//REQUEST_CODE_SELECT_IMAGE를 통해 앨범에서 보낸 요청에 대한 Callback인지를 체크!!!
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
//data.data에는 앨범에서 선택한 사진의 Uri가 들어있습니다!! 그러니까 제대로 선택됐는지 null인지 아닌지를 체크!!!
                if(data != null){
                 val selectedImageUri : Uri = data.data
                    selectedImageUri2= selectedImageUri
                    imageURI = getRealPathFromURI(selectedImageUri)
                    dataList.add(ChatRoomData("",selectedImageUri.toString(),true))
                    rl_message_chat.adapter!!.notifyDataSetChanged()
              }
            }

        }
    }
    override fun onPause() {
        super.onPause()

    }

    override fun onRestart() {
        super.onRestart()


    }


    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
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
    fun showAlbum(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
    }
    fun chattingPost(){
        var jsonObject = JSONObject()
        jsonObject.put("Name", intent.getStringExtra("name"))
        jsonObject.put("Text", edt_message.text.toString())

          //Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        var postChatResponse: Call<PostChatResponse> = SqnetworkService.postChatResponse("application/json",SharedPreferenceController.getSQAuthorization(this),gsonObject)
        postChatResponse.enqueue(object : Callback<PostChatResponse> {
            override fun onResponse(call: Call<PostChatResponse>?, response: Response<PostChatResponse>?) {
                if (response!!.isSuccessful) {
                    if(response.body()!!.message=="성공"){
                        Handler().postDelayed(Runnable {
                            dataList.add(ChatRoomData(response.body()!!.result.RText.toString(),"",false))
                            rl_message_chat.adapter!!.notifyDataSetChanged()
                        }, 500)//

                        edt_message.setText("")
                    }
                }
                else{
                    Log.v("TAG", "채팅 실패")
                }
            }
            override fun onFailure(call: Call<PostChatResponse>?, t: Throwable?) {
                Log.v("TAG", "통신 실패 = " +t.toString())
            }
        })
    }
}
