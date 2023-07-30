package com.example.cryptable

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    var imageEncrypt: ConstraintLayout? = null
    var textEncrypt: ConstraintLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        imageEncrypt = findViewById(R.id.img_encrypt)
        textEncrypt = findViewById(R.id.text_encrypt)
        val capturedImageEncrypt = imageEncrypt
        capturedImageEncrypt?.setOnClickListener(View.OnClickListener {
            val imgIntent = Intent(this@MainActivity, ImageEncrypt::class.java)
            startActivity(imgIntent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        })

        val capturedTextEncrypt = textEncrypt
        capturedTextEncrypt?.setOnClickListener(View.OnClickListener {
            val textIntent = Intent(this@MainActivity, TextEncrypt::class.java)
            startActivity(textIntent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        })
    }
}