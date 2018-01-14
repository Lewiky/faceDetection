package com.limoncello.facedetection


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.graphics.BitmapFactory
import android.view.View


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mFaceOverlayView = findViewById<View>(R.id.face_overlay) as FaceOverlayView
        val stream = resources.openRawResource(R.raw.george)
        val bitmap = BitmapFactory.decodeStream(stream)
        mFaceOverlayView.setBitmap(bitmap)
    }

}
