package com.capstone.pantauharga.ui.splashscreen

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pantauharga.R
import com.capstone.pantauharga.databinding.ActivitySplashScreenBinding
import android.content.Intent
import com.capstone.pantauharga.ui.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView: ImageView = binding.imageView
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        imageView.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                val options = android.app.ActivityOptions.makeCustomAnimation(
                    this@SplashScreenActivity,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )

                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}