package com.example.ng_videojoc.models.items

import android.graphics.Bitmap
import android.graphics.PointF
import com.example.ng_videojoc.models.Bullet
import com.example.ng_videojoc.models.Enemy
import com.example.ng_videojoc.models.base.GameEntity
import com.example.ng_videojoc.models.bitmapsCollide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentLinkedQueue

class Weapon(
    var bitmap: Bitmap,
    val weaponAnimation: List<Pair<Bitmap, Long>>,
    val bBullet: Bitmap,
    val bulletBuffers: List<Pair<Pair<PointF, Int>, MutableList<Bullet>>>,
    val bulletAnim: List<Pair<Bitmap, Long>>,
    var bulletSpeed: Float
) : GameEntity()
{

    init {
        bitmap = weaponAnimation[0].first
    }

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
            for(bulletBuffer in bulletBuffers)
            {
                delay(bulletBuffer.first.second.toLong())
                val bullet = Bullet(bBullet, playerPosition, bulletBuffer.first.first, bulletAnim, bulletSpeed);
                bulletBuffer.second.add(bullet)
            }
        }

        anim()
    }

    fun update(enemies: ConcurrentLinkedQueue<Enemy>)
    {
        for(bulletBuffer in bulletBuffers)
        {
            for(a in bulletBuffer.second) {
                a.update()
                enemies.forEachIndexed { i, it ->
                    if(bitmapsCollide(it.bitmap, it.position, a.bitmap, a.position, 145))
                    {
                        enemies.remove(it)
                        bulletBuffer.second.remove(a)
                    }
                }
            }
        }
    }
}