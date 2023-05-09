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

class EnchancerGenerator(val context: Context, val screenSize: Point) {
    val utils = Utils(context)

    private val enchanceList = mutableListOf<Enchancer>()

    val bubble = Enchancer(utils.loadBitmap(R.drawable.bubble01, 4), screenSize, "bubble")

    init {
       // enchanceList.add(Enchancer(utils.loadBitmap(R.drawable.zapper_01, 4), screenSize, "zapper"))
        //enchanceList.add(Enchancer(utils.loadBitmap(R.drawable.bubble01, 4), screenSize, "bubble"))
        //enchanceList.add(Enchancer(utils.loadBitmap(R.drawable.hearthsized, 4), screenSize, "health"))
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
            if(i.status)
            {
                canvas.drawBitmap(i.bitmap, i.position.x, i.position.y, null)
            }
        }
    }

    fun shouldGenerate(): Boolean {
        val numeroAleatorio = (0..1000).random()
        //return numeroAleatorio <= 6
        return numeroAleatorio <= 3
    }

    fun update(player : Player)
    {
        if(shouldGenerate())
        {
            when((1..3).random())
            {
                1 -> enchanceList.add(Enchancer(utils.loadBitmap(R.drawable.zapper_01, 4), screenSize, "zapper"))
                2 -> enchanceList.add(Enchancer(utils.loadBitmap(R.drawable.bubble01, 4), screenSize, "bubble"))
                3 -> enchanceList.add(Enchancer(utils.loadBitmap(R.drawable.hearthsized, 4), screenSize, "health"))
            }
        }

        for (i in enchanceList)
        {
            if(i.status && bitmapsCollide(player.bSpaceship, player.position, i.bitmap, i.position)){
                i.status = false
                when (i.name) {
                    "bubble" -> player.setBubble()
                    "zapper" -> {
                        player.setWeaponZapper()
                    }
                    "health" -> player.heal()
                    else -> {}
                }
            }
            i.update()
        }
    }
}