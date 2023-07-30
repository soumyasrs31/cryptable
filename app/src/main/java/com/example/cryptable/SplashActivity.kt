package com.example.cryptable

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptable.MainActivity
import com.example.cryptable.R

class SplashActivity : AppCompatActivity() {
    private val Splash_Screen = 4000
    private var LogoImg: ImageView? = null
    private val Appname: TextView? = null
    private var topAnimation: Animation? = null
    private val leftAnimation: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        LogoImg = findViewById(R.id.SplashPC)
        //Appname = findViewById(R.id.SplashKeyboard);
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        //leftAnimation = AnimationUtils.loadAnimation(this, R.anim.left_aimation);
        LogoImg?.setAnimation(topAnimation)
        //Appname.setAnimation(leftAnimation);
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, Splash_Screen.toLong())
    }
}