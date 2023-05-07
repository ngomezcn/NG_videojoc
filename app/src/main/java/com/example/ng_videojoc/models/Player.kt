package com.example.ng_videojoc.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import android.util.SizeF
import com.example.ng_videojoc.R
import com.example.ng_videojoc.WeaponFactory
import com.example.ng_videojoc.models.base.GameEntity
import com.example.ng_videojoc.models.items.Weapon
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentLinkedQueue

class Player(val context: Context, val screen: Point) : GameEntity() {
    val utils = Utils(context)

    var bSpaceship: Bitmap
    var bBurn: Bitmap
    var bEngine: Bitmap
    var destroyed = false

    var bBubble: Bitmap? = null

    var oWeapon : Weapon
    var shipStatusHealth = listOf<Bitmap>(
        utils.loadBitmap(R.drawable.ship_very_damaged),
        utils.loadBitmap(R.drawable.ship_damaged),
        utils.loadBitmap(R.drawable.ship_slight_damage),
        utils.loadBitmap(R.drawable.ship_full))

    var speed = 0
    override var position = PointF()
    override var size: SizeF = SizeF((screen.x / 10f) * 2f, (screen.x / 10f) * 2f)

    init{
        bSpaceship = utils.loadBitmap(R.drawable.ship_full)
        bBurn = utils.loadBitmap(R.drawable.engine_idle_02)
        bEngine = utils.loadBitmap(R.drawable.baseengine)

        position.x = screen.x / 2f
        position.y = screen.y-800f

        val weaponFactory = WeaponFactory(context, utils, screen, position)
        oWeapon = weaponFactory.getAutoCannon()

        animation()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bBurn, position.x, position.y, null)
        canvas.drawBitmap(oWeapon.bitmap, position.x, position.y, null)

        canvas.drawBitmap(bEngine, position.x, position.y, null)
        canvas.drawBitmap(bSpaceship, position.x, position.y, null)

        if(bBubble != null){
            canvas.drawBitmap(bBubble!!, position.x+10f, position.y+10, null)
        }

        for(bulletBuffer in oWeapon.bulletBuffers)
        {
            for (bullet in  bulletBuffer.second)  // Buffer de balas disparadas
            {
                canvas.drawBitmap(bullet.bitmap, bullet.position.x, bullet.position.y, null)
            }
        }
    }

    fun updatePlayer(enemies : ConcurrentLinkedQueue<Enemy>){
        oWeapon.update(enemies)

        for (i in enemies)
        {
            if(i.bullet != null)
            {
                if(bitmapsCollide(bSpaceship, position, i.bullet!!.bitmap, i.bullet!!.position)){
                    health--

                    if(health==0)
                    {
                        destroyed = true
                    } else
                    {
                        bSpaceship = shipStatusHealth[health]
                        i.bullet = null
                    }
                }
            }
        }

        if(position.x > 0 && position.x < screen.x - size.width)
        {
            position.x += speed
            return
        } else
        {
            if(position.x < 1f)
            {
                position.x = 0.01f
            } else
            {
                position.x = screen.x - size.width - 0.01f
            }
        }


    }



    fun animation()
    {
        val a = listOf(R.drawable.engine_idle_01, R.drawable.engine_idle_02, R.drawable.engine_idle_03)
        CoroutineScope(Dispatchers.IO).launch {
            var index = 0
            while (true)
            {
                bBurn = utils.loadBitmap(a[index])
                delay((400..1000).random().toLong())

                index++
                if(index == a.size)
                {
                    index = 0
                }
            }
        }
    }

    fun shot() {
        oWeapon.shot(position)
    }

    fun setBubble() {
        bBubble = utils.loadBitmap(R.drawable.bubble01, 5)
    }

    var health = 4
    fun checkImpact(enemies : List<Enemy>)
    {

    }
}
