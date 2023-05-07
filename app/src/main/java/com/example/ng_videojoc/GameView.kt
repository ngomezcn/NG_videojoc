package com.example.ng_videojoc

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceView
import com.example.ng_videojoc.models.Bullet
import com.example.ng_videojoc.models.Enemy
import com.example.ng_videojoc.models.Player
import com.example.ng_videojoc.models.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentLinkedQueue

class GameView(context: Context, private val screenSize: Point) : SurfaceView(context) {
    val utils = Utils(context)

    var canvas: Canvas = Canvas()
    //var backgroundImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.background_1)
    var backgroundImage: Bitmap
    val viewModel by viewModels<ViewModel>()

    var shotAction = false
    val dest = Rect(0,0, screenSize.x, screenSize.y)
    val paint: Paint = Paint()

    val bullets : MutableList<Bullet> = mutableListOf()
    val bullets2 : MutableList<Bullet> = mutableListOf()

    var playing = true
    var score = 0

    var enchancerGenerator : EnchancerGenerator

    lateinit var player : Player

    var testB: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.background_1)

    lateinit var enemies : ConcurrentLinkedQueue<Enemy>

    init {
        //animationBa()
        enemies = ConcurrentLinkedQueue()

        enemies.add(Enemy(context, screenSize, utils.loadBitmap(R.drawable.ship1, 4), utils.loadBitmap(R.drawable.bola01),50f))
        enemies.add(Enemy(context, screenSize, utils.loadBitmap(R.drawable.ship3, 3), utils.loadBitmap(R.drawable.bola01),250f))
        enemies.add(Enemy(context, screenSize, utils.loadBitmap(R.drawable.ship3, 3), utils.loadBitmap(R.drawable.bola01),450f))
        enemies.add(Enemy(context, screenSize, utils.loadBitmap(R.drawable.ship6, 3), utils.loadBitmap(R.drawable.bola01),750f))
        enemies.add(Enemy(context, screenSize, utils.loadBitmap(R.drawable.ship3, 3), utils.loadBitmap(R.drawable.bola01),1000f))

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
        enchancerGenerator = EnchancerGenerator(context, screenSize)


        startGame()
    }

    fun startGame(){
        CoroutineScope(Dispatchers.Main).launch{
            while(playing){
                draw()
                update()
                delay(10)

                if(player.destroyed)
                {
                    break
                }
            }
        }
    }

    lateinit var indexs : MutableList<Float>
    var colum = 0
    var row = 0

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

            enchancerGenerator.draw(canvas)

            for(i in enemies)
            {
                i.draw(canvas)

            }

            player.draw(canvas)

            //SCORE
            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText("Score: $score", (screenSize.x - paint.descent()), 75f, paint)

            //ENEMY
            //canvas.drawBitmap(enemy.bitmap, enemy.positionX.toFloat(),0f, paint)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun update(){
        //enemy.updateEnemy()

        enchancerGenerator.update(player)
        player.updatePlayer(enemies)
        if(bullets.size != 0) {
            for (bullet in bullets)
            {
                bullet.update()
            }
            for (bullet in bullets2)
            {
                bullet.update()
            }
            //bullet.speed = 20
            //shot.updateShot()
        }
        for(i in enemies)
        {
            i.update()
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

                    player.position.x = event.x-180
                    /*if(event.x>player.playerPosition.x){
                        player.speed = 10
                    } else if (event.x < player.playerPosition.x)
                    {
                        player.speed = -10
                    } else
                    {
                    }*/
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