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
import com.example.cryptable.AESText.decrypt

class TextDecoder : AppCompatActivity() {
    var enterText: EditText? = null
    var decryptedText: TextView? = null
    var decryptBtn: Button? = null
    var copyTextBtn: Button? = null
    var clipboardManager: ClipboardManager? = null
    val secretKey = "ssshhhhhhhhhhh!!!!"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_decoder)
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.title = "Text Decryption"
        actionBar.show()
        enterText = findViewById(R.id.enter_enctext_editText)
        decryptedText = findViewById(R.id.tv_decrypted_txt)
        decryptBtn = findViewById(R.id.decrypt_btn)
        copyTextBtn = findViewById(R.id.copy_txt_btn_dec)
        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun dec(view: View?) {
        val temp = enterText!!.text.toString()
        val rv = decrypt(temp, secretKey)
        decryptedText!!.text = rv
        closeKeyboardD()
    }

    fun copyDec(view: View?) {
        val data = decryptedText!!.text.toString().trim { it <= ' ' }
        if (!data.isEmpty()) {
            val temp = ClipData.newPlainText("text", data)
            clipboardManager!!.setPrimaryClip(temp)
            Toast.makeText(this, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun closeKeyboardD() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}