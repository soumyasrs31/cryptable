package com.example.cryptable

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.io.IOException

class ImageEncrypt : AppCompatActivity() {
    var encryptBtn: Button? = null
    var decryptBtn: Button? = null
    var sImage: String? = null
    var imageView: ImageView? = null
    var encImgToText: EditText? = null
    var clipboardManager: ClipboardManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_encrypt)
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setTitle("Crypt Image")
        actionBar.show()
        val encryptBtn: Button = findViewById(R.id.enc_img_btn)
        val decryptBtn: Button = findViewById(R.id.dec_img_btn)

        encImgToText = findViewById(R.id.encText)
        imageView = findViewById(R.id.imageView)
        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        encryptBtn.setOnClickListener(View.OnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this@ImageEncrypt,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@ImageEncrypt,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    100
                )
            } else {
                selectImage()
            }
        })
        val capturedImageView = imageView
        decryptBtn.setOnClickListener(View.OnClickListener {
            val textToDecrypt = encImgToText?.text?.toString()
            if (textToDecrypt != null && capturedImageView != null) {
                val bytes = Base64.decode(textToDecrypt, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                capturedImageView.setImageBitmap(bitmap)
            }
        })


    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectImage()
        } else {
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            val bitmap: Bitmap
            var source: ImageDecoder.Source? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                source = ImageDecoder.createSource(this.contentResolver, uri!!)
                try {
                    bitmap = ImageDecoder.decodeBitmap(source)
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    val bytes = stream.toByteArray()
                    sImage = Base64.encodeToString(bytes, Base64.DEFAULT)
                    encImgToText!!.setText(sImage)
                    Toast.makeText(
                        this,
                        "Image encrypted! Click on Decrypt to restore!",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun copyImgCode(view: View?) {
        val data = encImgToText!!.text.toString().trim { it <= ' ' }
        if (!data.isEmpty()) {
            val temp = ClipData.newPlainText("text", data)
            clipboardManager!!.setPrimaryClip(temp)
            Toast.makeText(this, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
        }
    }
}