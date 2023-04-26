package com.example.ng_videojoc.models.base

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.util.SizeF

open class GameEntity {
    open val position = PointF()
    open lateinit var size : SizeF

    open fun draw(canvas : Canvas) {
    }
}