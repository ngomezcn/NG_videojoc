package com.example.ng_videojoc.models

import android.graphics.Bitmap
import android.graphics.PointF
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Math.PI
import java.lang.Math.sin

class Bullet(
    var bitmap: Bitmap,
    val spawnPosition: PointF,
    val fixedPosition: PointF,
    val bulletAnim: List<Pair<Bitmap, Long>>,
    val speed: Float
    )  {

    val position = PointF(spawnPosition.x+fixedPosition.x, spawnPosition.y+fixedPosition.y)

    init{
        //position.x += 10f
        //position.y += fPosition.y
        animation()
    }

    fun animation()
    {
        CoroutineScope(Dispatchers.IO).launch {
            var index = 0
            while (true)
            {
                bitmap = bulletAnim[index].first
                delay(bulletAnim[index].second)

                index++
                if(index == bulletAnim.size)
                {
                    index = 0
                }
            }
        }
    }

    val frecuencia = 1.0
    val amplitud = 0.3
    val duracion = 0.1
    val intervaloMuestreo = 0.01

    val muestras = (duracion / intervaloMuestreo).toInt()

    var index = 0

    fun update(){

       /* val tiempo = index * intervaloMuestreo
        val valor = amplitud * sin(2 * PI * frecuencia * tiempo)
        println(valor)
        position.x += valor.toFloat()*/
        position.y += speed
        index++
    }
}
