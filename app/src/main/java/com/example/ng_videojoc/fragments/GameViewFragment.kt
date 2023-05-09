package com.example.ng_videojoc.fragments

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import com.example.ng_videojoc.GameView
import com.example.ng_videojoc.R
import com.example.ng_videojoc.ViewModel
import com.example.ng_videojoc.databinding.FragmentGameViewBinding
import kotlinx.coroutines.delay

class GameViewFragment : Fragment() {
    lateinit var binding: FragmentGameViewBinding
    lateinit var gameView: GameView
    lateinit var fireButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        gameView = GameView(requireContext(), size)

        val game: FrameLayout = FrameLayout(requireContext())
        val gameButtons: RelativeLayout = RelativeLayout(requireContext())
        fireButton  = Button(requireContext());
        fireButton.setText("Fire")
        fireButton.setBackgroundColor(Color.RED)
        val b1 = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.FILL_PARENT,
            RelativeLayout.LayoutParams.FILL_PARENT
        )

        gameButtons.setLayoutParams(params)
        gameButtons.addView(fireButton)
        b1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
        b1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        fireButton.setLayoutParams(b1)
        game.addView(gameView)
        game.addView(gameButtons)

        return game
    }
    private val handler = Handler(Looper.getMainLooper())
    private var canShoot = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fireButton.setOnClickListener {
            if (canShoot) {
                canShoot = false

                fireButton.setBackgroundColor(Color.BLACK)
                gameView.shot()

                handler.postDelayed({
                    fireButton.setBackgroundColor(Color.RED)

                    canShoot = true
                }, 2000)
            }
        }
    }
}