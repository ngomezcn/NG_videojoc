package com.example.ng_videojoc

import com.example.ng_videojoc.entities.Player
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.ng_videojoc.entities.Bullet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class GameView(context: Context, private val size: Point) : SurfaceView(context) {
    var canvas: Canvas = Canvas()
    var backgroundImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.background)
    var shotAction = false
    val dest = Rect(0,0, size.x, size.y)
    val paint: Paint = Paint()

    val bullets : MutableList<Bullet> = mutableListOf()

    var playing = true
    var score = 0

    lateinit var player : Player

    init {
        player = Player(context, size.x, size.y)
        startGame()
    }

    fun startGame(){
        CoroutineScope(Dispatchers.Main).launch{
            while(playing){
                draw()
                update()
                delay(10)
            }
        }
    }


    fun draw()
    {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            canvas.drawBitmap(backgroundImage, null, dest, null)
            canvas.drawBitmap(player.bitmap, player.positionX, size.y-800f, null)

            for (bullet in bullets)
            {
                canvas.drawBitmap(bullet.bitmap, bullet.positionX, bullet.positionY, null)
            }

            //SCORE
            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText("Score: $score", (size.x - paint.descent()), 75f, paint)

            //ENEMY
            //canvas.drawBitmap(enemy.bitmap, enemy.positionX.toFloat(),0f, paint)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun update(){
        //enemy.updateEnemy()
        player.updatePlayer()
        if(bullets.size != 0) {
            for (bullet in bullets)
            {
                bullet.updateBullet()
            }
            //bullet.speed = 20
            //shot.updateShot()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when(event.action){
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    // Modifiquem la velocitat del jugador perquè es mogui?
                    if(event.x>player.positionX){
                        player.speed = 10
                    } else if (event.x < player.positionX)
                    {
                        player.speed = -10
                    } else
                    {
                    }
                }
                MotionEvent.ACTION_UP -> {
                    player.speed = 0
                }
            }
        }
        return true
    }

    fun shot()
    {
        val bullet = Bullet(context, size.x, size.y, player.positionX)
        bullet.speed = -20
        bullets.add(bullet)
    }
}