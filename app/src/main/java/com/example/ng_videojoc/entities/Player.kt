package com.example.ng_videojoc.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.ng_videojoc.R

class Player(context: Context, val screenX: Int, val screenY: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.spaceship)
    val width = screenX / 10f
    val height = screenY / 10f
    var positionX : Float = screenX / 2f
    var speed = 0

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }

    fun updatePlayer(){
        if(positionX > 0 && positionX < screenX - width)
        {
            positionX += speed
            return
        } else
        {
            if(positionX < 1f)
            {
                positionX = 0.01f
            } else
            {
                positionX = screenX - width - 0.01f
            }
        }

    }
}
