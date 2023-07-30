package com.example.cryptable

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TextEncrypt : AppCompatActivity() {
    var encrypt: Button? = null
    var decrypt: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_encrypt)
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setTitle("Crypt Text")
        actionBar.show()
        encrypt = findViewById(R.id.enc_text_btn)
        decrypt = findViewById(R.id.dec_text_btn)
        val capturedEncrypt = encrypt
        capturedEncrypt?.setOnClickListener(View.OnClickListener {
            val enc = Intent(this@TextEncrypt, TextEncoder::class.java)
            startActivity(enc)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        })

        val capturedDecrypt = decrypt
        capturedDecrypt?.setOnClickListener(View.OnClickListener {
            val dec = Intent(this@TextEncrypt, TextDecoder::class.java)
            startActivity(dec)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        })

    }
}