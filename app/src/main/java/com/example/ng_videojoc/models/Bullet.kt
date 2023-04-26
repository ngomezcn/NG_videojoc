package com.example.ng_videojoc.models

import android.graphics.Bitmap
import com.example.ng_videojoc.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Bullet(
    var bitmap: Bitmap,
    val screenY: Int,
    val positionX: Float,
    val bulletAnim: List<Pair<Bitmap, Long>>
) {
    var speed = 0
    var positionY = screenY-800f

    init{
        animation()
    }

    fun animation()
    {
        /*val a = listOf(R.drawable.bullet_2, R.drawable.bullet_3, R.drawable.bullet_4, R.drawable.bullet_1)
        CoroutineScope(Dispatchers.IO).launch {
            var index = 0
            while (true)
            {
                bitmap = utils.loadBitmap(a[index])
                delay(50)

                index++
                if(index == a.size)
                {
                    index = 0
                }
            }
        }*/
    }



    fun updateBullet(){
        positionY += speed
    }
}
