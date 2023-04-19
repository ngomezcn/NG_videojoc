package com.example.ng_videojoc.entities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.ng_videojoc.R

class Bullet(context: Context, screenX: Int, screenY: Int, val positionX : Float) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.blaster_player)
    val width = screenX / 20f
    val height = screenY / 20f
    var speed = 0
    var positionY = screenY-800f

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }

    fun updateBullet(){
        positionY += speed
    }
}
