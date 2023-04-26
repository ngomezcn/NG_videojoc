package com.example.ng_videojoc.models

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Utils(val context: Context) {


    fun getBitmapFromDrawable(resources: Resources, drawableResId: Int, options: BitmapFactory.Options): Bitmap {
        val inputStream = resources.openRawResource(drawableResId)
        return BitmapFactory.decodeStream(inputStream, null, options)!!
    }

    fun loadBitmap(drawable: Int) : Bitmap
    {
        val myOptions = BitmapFactory.Options()
        myOptions.inDither = true
        myOptions.inScaled = false
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888
        myOptions.inDither = false
        myOptions.inPurgeable = true


        val b = getBitmapFromDrawable(context.resources, drawable, options = myOptions )
        return Bitmap.createScaledBitmap(b, b.width*7, b.height*7,true)
    }

    fun loadBitmap(drawable: Int, multiplier : Int) : Bitmap
    {
        val myOptions = BitmapFactory.Options()
        myOptions.inDither = true
        myOptions.inScaled = false
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888
        myOptions.inDither = false
        myOptions.inPurgeable = true


        val b = getBitmapFromDrawable(context.resources, drawable, options = myOptions )
        return Bitmap.createScaledBitmap(b, b.width*multiplier, b.height*multiplier,true)
    }
}