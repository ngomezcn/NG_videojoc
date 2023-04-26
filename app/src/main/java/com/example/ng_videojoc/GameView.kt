package com.example.ng_videojoc

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceView
import com.example.ng_videojoc.models.Bullet
import com.example.ng_videojoc.models.Player
import com.example.ng_videojoc.models.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameView(context: Context, private val screenSize: Point) : SurfaceView(context) {
    val utils = Utils(context)

    var canvas: Canvas = Canvas()
    //var backgroundImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.background_1)
    var backgroundImage: Bitmap

    var shotAction = false
    val dest = Rect(0,0, screenSize.x, screenSize.y)
    val paint: Paint = Paint()

    val bullets : MutableList<Bullet> = mutableListOf()
    val bullets2 : MutableList<Bullet> = mutableListOf()

    var playing = true
    var score = 0

    lateinit var player : Player

    fun animBack()
    {
        val a = listOf(R.drawable.engine_idle_01, R.drawable.engine_idle_02, R.drawable.engine_idle_03)
    }

    var testB: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.background_1)

    init {
        //animationBa()

        player = Player(context, screenSize)
        //backgroundImage = loadBitmap(R.drawable.background_1)
        backgroundImage = createTestBitmap(screenSize.x,screenSize.y)!!

        val matrix = Matrix()
        matrix.postRotate(90F)

        testB = loadBitmap(R.drawable.background_1)
        testB = Bitmap.createBitmap(
            testB,
            0,
            0,
            testB.width,
            testB.height,
            matrix,
            true
        )
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

    lateinit var indexs : MutableList<Float>
    var colum = 0
    var row = 0

    fun animationBa()
    {
        indexs = mutableListOf()
        colum = 1
        row = 3

        for (i in 0 until row)
        {
            if(i == 0)
            {
                indexs.add(0F)
            } else
            {
                indexs.add((testB.width * i).toFloat())
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            while (true)
            {
                delay(1)
                for (i in 0 until row)
                {
                    indexs[i] += 0.5f

                    if(indexs[i] > screenSize.y)
                    {
                        indexs[i] = -1F * testB.width
                    }
                }
            }
        }
    }

    fun draw()
    {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            canvas.drawBitmap(backgroundImage, 0f,0f, null)

            for (i in 0 until row)
            {
                for (j in 0 until colum)
                {
                    canvas.drawBitmap(testB, testB.width.toFloat() * j, indexs[i], null)
                }
            }

            player.draw(canvas)

            //SCORE
            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.RIGHT
            //canvas.drawText("Score: $score", (size.x - paint.descent()), 75f, paint)

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
            for (bullet in bullets2)
            {
                bullet.updateBullet()
            }
            //bullet.speed = 20
            //shot.updateShot()
        }
    }

    fun getBitmapFromDrawable(resources: Resources, drawableResId: Int, options: BitmapFactory.Options): Bitmap {
    val inputStream = resources.openRawResource(drawableResId)
    return BitmapFactory.decodeStream(inputStream, null, options)!!
    }
    fun loadBitmap(drawable: Int, width: Float? = null, height : Float? = null) : Bitmap
    {
        val myOptions = BitmapFactory.Options()
        myOptions.inDither = true
        myOptions.inScaled = false
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888
        myOptions.inDither = false
        myOptions.inPurgeable = true


        val b = getBitmapFromDrawable(context.resources, drawable, options = myOptions )
        return Bitmap.createScaledBitmap(b, b.width*6, b.height*6,true)
    }

    fun createTestBitmap(w: Int, h: Int): Bitmap? {
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        canvas.drawColor(Color.BLACK)
        return bitmap
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when(event.action){
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    // Modifiquem la velocitat del jugador perquè es mogui?
                    if(event.x>player.position.x){
                        player.speed = 10
                    } else if (event.x < player.position.x)
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
        player.shot()
    }
}