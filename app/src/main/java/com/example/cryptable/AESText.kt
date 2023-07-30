package com.example.cryptable

import android.os.Build
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AESText {
    private var secretKey: SecretKeySpec? = null
    private lateinit var key: ByteArray

    fun setKey(myKey: String) {
        var sha: MessageDigest? = null
        try {
            key = myKey.toByteArray(charset("UTF-8"))
            sha = MessageDigest.getInstance("SHA-1")
            key = sha.digest(key)
            key = Arrays.copyOf(key, 16)
            secretKey = SecretKeySpec(key, "AES")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun encrypt(strToEncrypt: String, secret: String): String? {
        try {
            setKey(secret)
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.toByteArray(charset("UTF-8"))))
            }
        } catch (e: Exception) {
            println("Error while encrypting: $e")
        }
        return null
    }

    @JvmStatic
    fun decrypt(strToDecrypt: String?, secret: String): String? {
        try {
            setKey(secret)
            val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)))
            }
        } catch (e: Exception) {
            println("Error while decrypting: $e")
        }
        return null
    }
}