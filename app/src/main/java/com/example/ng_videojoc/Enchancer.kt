package com.example.ng_videojoc

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import com.example.ng_videojoc.models.base.GameEntity
import kotlinx.coroutines.delay
import java.lang.reflect.Type
import java.util.Random

class Enchancer(val bitmap: Bitmap, val screenSize : Point, val name: String, var status : Boolean = true) : GameEntity(){
    override var position = PointF()

    var xm : Float = (15f + Math.random() * (25f - 15f)).toFloat();


    init {
        position.x = (screenSize.x/2).toFloat()
        position.y -= bitmap.height

        val random = Random()
        if(random.nextBoolean())
        {
            xm *= -1f
        }
    }

    fun update()
    {
        position.y += 8f
        position.x += xm
        if (position.x + bitmap.width >= screenSize.x) {
            xm *= -1
        }
        if (position.x <= 0.0f) {
            xm *= -1
        }
        val rand = (0..50).random().toLong()
    }
}