package com.example.ng_videojoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ng_videojoc.databinding.ActivityMainBinding
import com.example.ng_videojoc.fragments.GameViewFragment

// https://foozlecc.itch.io/void-main-ship

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var navHostFragment: NavHostFragment
    lateinit var navControllerDrawer: NavController
    lateinit var drawerLayout : DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, GameViewFragment())
            setReorderingAllowed(true)
            addToBackStack("name") // name can be null
            commit()
        }
    }
}