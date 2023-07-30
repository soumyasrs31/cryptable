package com.example.cryptable

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptable.AESText.encrypt

class TextEncoder : AppCompatActivity() {
    var enterText: EditText? = null
    var encryptedText: TextView? = null
    var encryptBtn: Button? = null
    var copyTextBtn: Button? = null
    var clipboardManager: ClipboardManager? = null
    val secretKey = "ssshhhhhhhhhhh!!!!"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_encoder)
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.title = "Text Encryption"
        actionBar.show()
        enterText = findViewById(R.id.enter_text_editText)
        encryptedText = findViewById(R.id.tv_encrypted_txt)
        encryptBtn = findViewById(R.id.encrypt_btn)
        copyTextBtn = findViewById(R.id.copy_text_btn)
        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun enc(view: View?) {
        val temp = enterText!!.text.toString()
        val rv = encrypt(temp, secretKey)
        encryptedText!!.text = rv
        closeKeyboard()
    }

    fun copy(view: View?) {
        val data = encryptedText!!.text.toString().trim { it <= ' ' }
        if (!data.isEmpty()) {
            val temp = ClipData.newPlainText("text", data)
            clipboardManager!!.setPrimaryClip(temp)
            Toast.makeText(this, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}