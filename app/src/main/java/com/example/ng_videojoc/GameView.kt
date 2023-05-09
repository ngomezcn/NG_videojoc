package com.example.ng_videojoc

import android.R.attr
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceView
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
    var backgroundImage: Bitmap

    val paint: Paint = Paint()
    var playing = true
    var enchancerGenerator : EnchancerGenerator
    var player : Player
    var testB: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.background_1)
    var enemies : ConcurrentLinkedQueue<Enemy>

    init {
        enemies = ConcurrentLinkedQueue()

        enemies.add(Enemy(context, screenSize, utils.loadBitmap(R.drawable.ship1, 4), utils.loadBitmap(R.drawable.bola01),50f))
        enemies.add(Enemy(context, screenSize, utils.loadBitmap(R.drawable.ship3, 3), utils.loadBitmap(R.drawable.bola01),250f))
        enemies.add(Enemy(context, screenSize, utils.loadBitmap(R.drawable.ship3, 3), utils.loadBitmap(R.drawable.bola01),450f))
        enemies.add(Enemy(context, screenSize, utils.loadBitmap(R.drawable.ship6, 3), utils.loadBitmap(R.drawable.zapprojectile01),750f))
        enemies.add(Enemy(context, screenSize, utils.loadBitmap(R.drawable.ship3, 3), utils.loadBitmap(R.drawable.bola01),1000f))

        player = Player(context, screenSize)
        //backgroundImage = loadBitmap(R.drawable.background_1)

        backgroundImage = utils.loadBitmap(R.drawable.wallpaper_1, 3)

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

            if(!player.destroyedAnim)
            {
                enchancerGenerator.draw(canvas)
            }

            for(i in enemies)
            {
                i.draw(canvas)
            }

            player.draw(canvas)

            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.RIGHT
            player.score -= 0.05f
            canvas.drawText("Score: ${player.score.toInt()}", (screenSize.x - paint.descent()), 75f, paint)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun update(){
        enchancerGenerator.update(player)
        player.updatePlayer(enemies)
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



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when(event.action){
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    if(!player.destroyedAnim) {
                        player.position.x = event.x - 180
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