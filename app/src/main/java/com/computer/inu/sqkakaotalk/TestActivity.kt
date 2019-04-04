package com.computer.inu.sqkakaotalk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.daimajia.swipe.SwipeLayout

class TestActivity : AppCompatActivity() {

    private var swipe_sample1: SwipeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        swipe_sample1 = findViewById(R.id.swipe_sample1) as SwipeLayout
        swipe_sample1!!.showMode = SwipeLayout.ShowMode.LayDown
        //오른쪽에서 나오는 drag (tag로 설정한 HideTag가 보여짐
        swipe_sample1!!.addDrag(SwipeLayout.DragEdge.Right, swipe_sample1!!.findViewWithTag("HideTag"))
        //swipe_layout을 클릭한 경우
        swipe_sample1!!.surfaceView.setOnClickListener {
            Toast.makeText(
                this@TestActivity,
                "Click on surface",
                Toast.LENGTH_SHORT
            ).show()
        }
        //star버튼을 클릭한 경우
    }
}
