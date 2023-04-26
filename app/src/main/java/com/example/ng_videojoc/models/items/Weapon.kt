package com.example.ng_videojoc.models.items

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import com.example.ng_videojoc.models.Bullet
import com.example.ng_videojoc.models.base.GameEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Weapon(
    var bitmap: Bitmap,
    val weaponAnimation: List<Pair<Bitmap, Long>>,
    val bBullet: Bitmap,
    val bulletsConfig: List<Pair<Point, MutableList<Bullet>>>,
    val bulletAnim: List<Pair<Bitmap, Long>>
) : GameEntity()
{
    fun anim()
    {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in weaponAnimation) {
                bitmap = i.first
                delay(i.second)
            }
        }
    }

    fun shot(playerPosition : PointF)
    {
        CoroutineScope(Dispatchers.IO).launch {
            anim()

            for(bc in bulletsConfig)
            {
                val bullet = Bullet(bBullet, bc.first.x, bc.first.y-30f, bulletAnim)
                bc.second.add(bullet)
            }
        }
    }

    override fun draw(canvas : Canvas)
    {

    }
}