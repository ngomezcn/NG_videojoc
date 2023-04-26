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

class Player(val context: Context, val screen: Point) : GameEntity() {
    val utils = Utils(context)

    var bSpaceship: Bitmap
    var bBurn: Bitmap
    var bEngine: Bitmap

    var oWeapon : Weapon

    var speed = 0
    override val position = PointF()
    override var size: SizeF = SizeF((screen.x / 10f) * 2f, (screen.x / 10f) * 2f)

    init{
        bSpaceship = utils.loadBitmap(R.drawable.ship_full)
        bBurn = utils.loadBitmap(R.drawable.engine_idle_02)
        bEngine = utils.loadBitmap(R.drawable.baseengine)

        position.x = screen.x / 2f

        val weaponFactory = WeaponFactory(context, utils, screen)
        oWeapon = weaponFactory.getAutoCannon()

        animation()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bBurn, position.x, screen.y-800f, null)
        canvas.drawBitmap(oWeapon.bitmap, position.x, screen.y-800f, null)

        for(bc in oWeapon.bulletsConfig)
        {
            for (i in  bc.second)
            {
                canvas.drawBitmap(i.bitmap, i.positionX-10, i.positionY, null)
            }
        }

        //for (bullet in bullets)
        //     canvas.drawBitmap(bullet.bitmap, bullet.positionX-10, bullet.positionY, null)
        //for (bullet in bullets2)
        //    canvas.drawBitmap(bullet.bitmap, bullet.positionX+120, bullet.positionY, null)
        canvas.drawBitmap(bEngine, position.x, screen.y-800f, null)
        canvas.drawBitmap(bSpaceship, position.x, screen.y-800f, null)
        // canvas.drawBitmap(player.shield, player.positionX, size.y-800f, null)
    }

    fun updatePlayer(){
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


}
