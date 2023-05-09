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
import kotlin.math.exp

class Player(val context: Context, val screen: Point) : GameEntity() {
    val utils = Utils(context)

    var bSpaceship: Bitmap
    var bBurn: Bitmap
    var bEngine: Bitmap
    var bExplosion: Bitmap? = null
    var destroyed = false

    var bBubble: Bitmap? = null

    var score : Float = 1000f

    var oWeapon : Weapon
    var shipStatusHealth = listOf<Bitmap>(
        utils.loadBitmap(R.drawable.ship_very_damaged),
        utils.loadBitmap(R.drawable.ship_damaged),
        utils.loadBitmap(R.drawable.ship_slight_damage),
        utils.loadBitmap(R.drawable.ship_full))

    lateinit var canvas2 : Canvas

    var speed = 0
    override var position = PointF()
    override var size: SizeF = SizeF((screen.x / 10f) * 2f, (screen.x / 10f) * 2f)
    val weaponFactory = WeaponFactory(context, utils, screen, position)

    init{
        bSpaceship = utils.loadBitmap(R.drawable.ship_full)
        bBurn = utils.loadBitmap(R.drawable.engine_idle_02)
        bEngine = utils.loadBitmap(R.drawable.baseengine)

        position.x = screen.x / 2f
        position.y = screen.y-800f

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

        if(bExplosion != null)
        {
            canvas.drawBitmap(bExplosion!!, position.x, position.y-50, null)

        }
    }

    fun updatePlayer(enemies : ConcurrentLinkedQueue<Enemy>){
        oWeapon.update(enemies)

        for (i in enemies)
        {
            if(i.bullet != null)
            {
                if(bitmapsCollide(bSpaceship, position, i.bullet!!.bitmap, i.bullet!!.position)){

                    if(bBubble == null)
                    {
                        health--
                    } else
                    {
                        bBubble = null
                    }

                    if(health<0)
                    {
                        bExplosion = utils.loadBitmap(R.drawable.tile000)
                        explode()
                    } else
                    {
                        score -= 50f
                        if(score < 0)
                        {
                            score = 0f
                        }
                        calculateDamage()
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

    fun calculateDamage()
    {
        bSpaceship = shipStatusHealth[health]
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

    var maxHealth = 3
    var health = maxHealth
    fun setWeaponZapper() {
        oWeapon = weaponFactory.getZapper()
    }

    fun heal() {
        health = maxHealth
        calculateDamage()
    }

    var destroyedAnim = false
    var exploded = true
    fun explode()
    {
        destroyedAnim = true
        if(exploded)
        {
            exploded = false
            var exFrames = listOf<Bitmap>(
                utils.loadBitmap(R.drawable.tile000, 14),
                utils.loadBitmap(R.drawable.tile001, 14),
                utils.loadBitmap(R.drawable.tile002, 14),
                utils.loadBitmap(R.drawable.tile003, 14),
                utils.loadBitmap(R.drawable.tile004, 14),
                utils.loadBitmap(R.drawable.tile005, 14),
                utils.loadBitmap(R.drawable.tile006, 14),
                utils.loadBitmap(R.drawable.tile007, 14),
                utils.loadBitmap(R.drawable.tile008, 14),
                utils.loadBitmap(R.drawable.tile009, 14),
                utils.loadBitmap(R.drawable.tile010, 14),
                utils.loadBitmap(R.drawable.tile011, 14),
                utils.loadBitmap(R.drawable.tile012, 14),
                utils.loadBitmap(R.drawable.tile013, 14),
                utils.loadBitmap(R.drawable.tile014, 14),
                utils.loadBitmap(R.drawable.tile015, 14),
                utils.loadBitmap(R.drawable.tile016, 14),
                utils.loadBitmap(R.drawable.tile017, 14),
                utils.loadBitmap(R.drawable.tile018, 14),
                utils.loadBitmap(R.drawable.tile019, 14),
                utils.loadBitmap(R.drawable.tile020, 14),
                utils.loadBitmap(R.drawable.tile021, 14),
                utils.loadBitmap(R.drawable.tile022, 14),
                utils.loadBitmap(R.drawable.tile023, 14),
                utils.loadBitmap(R.drawable.tile024, 14),
                utils.loadBitmap(R.drawable.tile025, 14),
                utils.loadBitmap(R.drawable.tile026, 14),
                utils.loadBitmap(R.drawable.tile027, 14),
                utils.loadBitmap(R.drawable.tile028, 14),
                utils.loadBitmap(R.drawable.tile029, 14),
                utils.loadBitmap(R.drawable.tile030, 14),
                utils.loadBitmap(R.drawable.tile031, 14),
                utils.loadBitmap(R.drawable.tile032, 14),
                utils.loadBitmap(R.drawable.tile033, 14),
                utils.loadBitmap(R.drawable.tile034, 14),
                utils.loadBitmap(R.drawable.tile035, 14),
                utils.loadBitmap(R.drawable.tile036, 14),
                utils.loadBitmap(R.drawable.tile037, 14),
                utils.loadBitmap(R.drawable.tile038, 14),
                utils.loadBitmap(R.drawable.tile038, 14),
                utils.loadBitmap(R.drawable.tile039, 14),
                utils.loadBitmap(R.drawable.tile040, 14),
                utils.loadBitmap(R.drawable.tile041, 14),
                utils.loadBitmap(R.drawable.tile042, 14),
                utils.loadBitmap(R.drawable.tile043, 14),
                utils.loadBitmap(R.drawable.tile044, 14),
                utils.loadBitmap(R.drawable.tile045, 14),
                utils.loadBitmap(R.drawable.tile046, 14),
                utils.loadBitmap(R.drawable.tile047, 14),
                utils.loadBitmap(R.drawable.tile048, 14),
                utils.loadBitmap(R.drawable.tile050, 14),
                utils.loadBitmap(R.drawable.tile041, 14),
                utils.loadBitmap(R.drawable.tile042, 14),
                utils.loadBitmap(R.drawable.tile043, 14),
                utils.loadBitmap(R.drawable.tile044, 14),
                utils.loadBitmap(R.drawable.tile045, 14),
                utils.loadBitmap(R.drawable.tile046, 14),
                utils.loadBitmap(R.drawable.tile047, 14),
                utils.loadBitmap(R.drawable.tile048, 14),
                utils.loadBitmap(R.drawable.tile049, 14),
                utils.loadBitmap(R.drawable.tile050, 14),
                utils.loadBitmap(R.drawable.tile051, 14),
                utils.loadBitmap(R.drawable.tile052, 14),
                utils.loadBitmap(R.drawable.tile053, 14),
                utils.loadBitmap(R.drawable.tile054, 14),
                utils.loadBitmap(R.drawable.tile055, 14),
                utils.loadBitmap(R.drawable.tile056, 14),
                utils.loadBitmap(R.drawable.tile057, 14),
                utils.loadBitmap(R.drawable.tile058, 14),
                utils.loadBitmap(R.drawable.tile059, 14),
                utils.loadBitmap(R.drawable.tile060, 14),
                utils.loadBitmap(R.drawable.tile061, 14),
                utils.loadBitmap(R.drawable.tile062, 14),
                utils.loadBitmap(R.drawable.tile063, 14))

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main)
                {
                    for(i in exFrames)
                    {
                        delay(50)
                        bExplosion = i
                        bBubble = null
                        bSpaceship = shipStatusHealth[0]
                    }
                    delay(400)
                    destroyed = true

                }
            }
        }
    }
}
