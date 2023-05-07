package com.example.ng_videojoc

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import com.example.ng_videojoc.models.Player
import com.example.ng_videojoc.models.Utils
import com.example.ng_videojoc.models.bitmapsCollide
import com.example.ng_videojoc.models.items.Weapon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EnchancerGenerator(context: Context, screenSize: Point) {
    val utils = Utils(context)

    val enchanceList = mutableListOf<Enchancer>()

    val bubble = Enchancer(utils.loadBitmap(R.drawable.bubble01, 4), screenSize, "bubble")

    init {
        enchanceList.add(Enchancer(utils.loadBitmap(R.drawable.bubble01, 4), screenSize, "bubble"))
        launchEmitter()
    }

    private fun launchEmitter() {
        CoroutineScope(Dispatchers.IO).launch {

        }
    }

    fun draw(canvas: Canvas)
    {
        for (i in enchanceList)
        {
            canvas.drawBitmap(i.bitmap, i.position.x, i.position.y, null)
        }
    }

    fun update(player : Player)
    {
        for (i in enchanceList)
        {
            if(bitmapsCollide(player.bSpaceship, player.position, i.bitmap, i.position)){
                when (i.name) {
                    "bubble" -> player.setBubble()
                    "zapper" -> print("x == 1")
                    "health" -> print("x == 1")
                }
                enchanceList.remove(i)
            }
            i.update()
        }
    }
}