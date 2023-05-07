package com.example.ng_videojoc.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.PointF
import com.example.ng_videojoc.R
import java.util.Random

class Enemy(val context: Context, val screen: Point, val bitmap : Bitmap, val vertical : Float) {
    var speed = 0
    val utils = Utils(context)
    var position = PointF()
    var alive = true
    val random = Random()

    var xm = 0f

    init{
        position.x = (screen.x/2).toFloat()
        position.y = vertical

        xm = 5f + random.nextFloat() * (15f - 5f)

        if(random.nextBoolean())
        {
            xm *= -1f
        }
    }

    fun update(){
        position.x += xm
        if (position.x + bitmap.width >= screen.x) {
            xm *= -1
        }
        if (position.x <= 0.0f) {
            xm *= -1
        }
        val rand = (0..50).random().toLong()
    }
}
