package com.example.ng_videojoc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import com.example.ng_videojoc.models.Bullet
import com.example.ng_videojoc.models.Utils
import com.example.ng_videojoc.models.items.Weapon

class WeaponFactory(val context: Context, val utils: Utils, val screen: Point)
{
    fun getAutoCannon() : Weapon
    {
        val weaponAnim : List<Pair<Bitmap, Long>> =
            listOf(
                Pair(utils.loadBitmap(R.drawable.weapon_2), 100),
                Pair(utils.loadBitmap(R.drawable.weapon_3), 100),
                Pair(utils.loadBitmap(R.drawable.weapon_4), 50),
                Pair(utils.loadBitmap(R.drawable.weapon_5), 100),
                Pair(utils.loadBitmap(R.drawable.weapon_6), 50),
                Pair(utils.loadBitmap(R.drawable.weapon_7), 100),
                Pair(utils.loadBitmap(R.drawable.weapon_1), 50))

        val bulletAnim : List<Pair<Bitmap, Long>> =
            listOf(
                Pair(utils.loadBitmap(R.drawable.bullet_1), 50),
                Pair(utils.loadBitmap(R.drawable.bullet_2), 50),
                Pair(utils.loadBitmap(R.drawable.bullet_3), 50),
                Pair(utils.loadBitmap(R.drawable.bullet_4), 50))

        val bulletsNumber = listOf(Pair(Point(), mutableListOf<Bullet>()), Pair(Point(), mutableListOf<Bullet>()))

        val bBullet = utils.loadBitmap(R.drawable.bullet_1, 4)
        val bWeapon = utils.loadBitmap(R.drawable.weapon_1)

        return Weapon(bWeapon, weaponAnim, bBullet, bulletsNumber, bulletAnim)
    }
}