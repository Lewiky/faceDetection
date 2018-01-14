package com.limoncello.facedetection

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import com.google.android.gms.vision.face.Face
import com.google.android.gms.vision.face.FaceDetector
import android.R.attr.bitmap
import android.graphics.*
import android.support.v4.app.TaskStackBuilder
import com.google.android.gms.vision.Frame


class FaceOverlayView: View {

    private var mBitmap : Bitmap? = null
    private var mFaces : SparseArray<Face>? = null

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    public fun setBitmap(bitmap: Bitmap){
        mBitmap = bitmap

        val detector = FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build()

        if (!detector.isOperational) {
            throw NullPointerException()
        } else {
            val frame = Frame.Builder().setBitmap(bitmap).build()
            mFaces = detector.detect(frame)
            detector.release()
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)

        if(mBitmap != null && mFaces != null){
            val scale = drawBitmap(canvas)
            drawFaceBox(canvas,scale)
        }
    }

    private fun drawBitmap(canvas: Canvas): Double{
        val scale = Math.min(canvas.width/mBitmap!!.width,canvas.height/mBitmap!!.height).toDouble()
        val destBounds = Rect(0,0, ((mBitmap!!.width)*scale).toInt(), ((mBitmap!!.height)*scale).toInt())
        canvas.drawBitmap(mBitmap,null,destBounds,null)
        return scale
    }

    private fun drawFaceBox(canvas: Canvas,scale : Double){
        val paint = Paint()
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5.toFloat()

        for(i in 0 .. mFaces!!.size()-1){
            val faces = mFaces as SparseArray<Face>
            val face = faces.valueAt(i)

            val left = (face.position.x * scale).toFloat()
            val top = (face.position.y  * scale).toFloat()
            val right = (scale*(face.position.x + face.width)).toFloat()
            val bottom = (scale*(face.position.y + face.height)).toFloat()

            canvas.drawRect(left,top,right,bottom,paint)

        }
    }


}