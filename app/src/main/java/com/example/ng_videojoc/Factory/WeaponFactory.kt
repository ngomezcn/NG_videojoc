package com.example.ng_videojoc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.PointF
import com.example.ng_videojoc.models.Bullet
import com.example.ng_videojoc.models.Utils
import com.example.ng_videojoc.models.items.Weapon

class WeaponFactory(
    val context: Context,
    val utils: Utils,
    val screen: Point,
    val playerPosition: PointF
)
{
    fun getAutoCannon() : Weapon
    {
        val weaponAnim : List<Pair<Bitmap, Long>> =
            listOf(
                Pair(utils.loadBitmap(R.drawable.weapon_1), 50),
                Pair(utils.loadBitmap(R.drawable.weapon_2), 100),
                Pair(utils.loadBitmap(R.drawable.weapon_3), 150),
                Pair(utils.loadBitmap(R.drawable.weapon_4), 150),
                Pair(utils.loadBitmap(R.drawable.weapon_5), 150),
                Pair(utils.loadBitmap(R.drawable.weapon_6), 150),
                Pair(utils.loadBitmap(R.drawable.weapon_7), 50))

        val bulletAnim : List<Pair<Bitmap, Long>> =
            listOf(
                Pair(utils.loadBitmap(R.drawable.bullet_1), 50),
                Pair(utils.loadBitmap(R.drawable.bullet_2), 50),
                Pair(utils.loadBitmap(R.drawable.bullet_3), 50),
                Pair(utils.loadBitmap(R.drawable.bullet_4), 50))

        val bulletsNumber = listOf(Pair(Pair(PointF(-10f, -47f), 0), mutableListOf<Bullet>()), Pair(Pair(PointF(120f, -47f), 150), mutableListOf<Bullet>()))

        val bitmapBullet = utils.loadBitmap(R.drawable.bullet_1, 4)
        val bitmapWeapon = utils.loadBitmap(R.drawable.weapon_1)

        val bulletSpeed = -20f

        return Weapon(bitmapWeapon, weaponAnim, bitmapBullet, bulletsNumber, bulletAnim, bulletSpeed)
    }

    fun getZapper() : Weapon
    {
        val weaponAnim : List<Pair<Bitmap, Long>> =
            listOf(
                Pair(utils.loadBitmap(R.drawable.zapper_01), 200),
                Pair(utils.loadBitmap(R.drawable.zapper_02), 200),
                Pair(utils.loadBitmap(R.drawable.zapper_03), 200),
                Pair(utils.loadBitmap(R.drawable.zapper_04), 400),
                Pair(utils.loadBitmap(R.drawable.zapper_05), 300),
                Pair(utils.loadBitmap(R.drawable.zapper_06), 300),
                Pair(utils.loadBitmap(R.drawable.zapper_07), 300),
                Pair(utils.loadBitmap(R.drawable.zapper_08), 300),
                Pair(utils.loadBitmap(R.drawable.zapper_09), 300),
                Pair(utils.loadBitmap(R.drawable.zapper_10), 300),
                Pair(utils.loadBitmap(R.drawable.zapper_11), 300),
                Pair(utils.loadBitmap(R.drawable.zapper_12), 300),
                Pair(utils.loadBitmap(R.drawable.zapper_13), 100),
                Pair(utils.loadBitmap(R.drawable.zapper_14), 100))

        val bulletAnim : List<Pair<Bitmap, Long>> =
            listOf(
                Pair(utils.loadBitmap(R.drawable.zapprojectile01), 50),
                Pair(utils.loadBitmap(R.drawable.zapprojectile02), 50),
                Pair(utils.loadBitmap(R.drawable.zapprojectile03), 50),
                Pair(utils.loadBitmap(R.drawable.zapprojectile04), 50),
                Pair(utils.loadBitmap(R.drawable.zapprojectile05), 50),
                Pair(utils.loadBitmap(R.drawable.zapprojectile06), 50),
                Pair(utils.loadBitmap(R.drawable.zapprojectile07), 50),
                Pair(utils.loadBitmap(R.drawable.zapprojectile08), 50))

        val bulletsNumber = listOf(Pair(Pair(PointF(-3f, -140f), 900), mutableListOf<Bullet>()), Pair(Pair(PointF(117f, -140f), 0), mutableListOf<Bullet>()))

        val bitmapBullet = utils.loadBitmap(R.drawable.bullet_1, 4)
        val bitmapWeapon = utils.loadBitmap(R.drawable.weapon_1)

        val bulletSpeed = -20f

        return Weapon(bitmapWeapon, weaponAnim, bitmapBullet, bulletsNumber, bulletAnim, bulletSpeed)
    }
}