package com.gl4.commandpizza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashScreen : AppCompatActivity() {
    lateinit var topAnim : Animation
    lateinit var bottomAnim: Animation
    lateinit var image : ImageView
    lateinit var slogan : TextView
    private val SPALSH_SCREEN : Int = 5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        image = findViewById(R.id.imageView)
        slogan = findViewById(R.id.textView)

        image.animation = topAnim
        slogan.animation = bottomAnim

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPALSH_SCREEN.toLong())
    }
}