package com.example.cryptable

import DatabaseHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import android.content.Intent

class RegisterActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        databaseHelper = DatabaseHelper(this)
        etUsername = findViewById(R.id.et_username)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnRegister = findViewById(R.id.btn_register)

        etUsername.doOnTextChanged { _, _, _, _ -> clearError() }
        etEmail.doOnTextChanged { _, _, _, _ -> clearError() }
        etPassword.doOnTextChanged { _, _, _, _ -> clearError() }

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val userId = databaseHelper.insertUser(username, email, password) { userId ->
                    if (userId != -1L) {
                        // Registration successful, show success message and navigate to login activity
                        showToast("Account created successfully")
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Registration failed, show error message
                        showError("Registration failed")
                    }
                }

            } else {
                // Fields are empty, show error message
                showError("Please fill all fields")
            }
        }
    }

    private fun clearError() {
        // Clear any previous error message
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(errorMessage: String) {
        // Show the error message to the user
    }
}