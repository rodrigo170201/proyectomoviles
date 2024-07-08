// RegisterActivity.kt
package com.example.loginapi.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapi.databinding.ActivityRegisterBinding
import com.example.loginapi.ui.viewmodels.MainViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEventListeners()
        observeViewModel()
    }

    private fun setupEventListeners() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val phone = binding.etPhone.text.toString()

            // Log the data to ensure they are correctly fetched
            Log.d("RegisterActivity", "Register data: Name: $name, Email: $email, Password: $password, Phone: $phone")

            viewModel.register(name, email, password, phone)
        }
    }

    private fun observeViewModel() {
        viewModel.registerSuccess.observe(this, { success ->
            if (success) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                Log.d("RegisterActivity", "Registro exitoso")
                finish()
            } else {
                val errorMessage = viewModel.errorMessage.value ?: "registrado"
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("RegisterActivity", errorMessage)
            }
        })

        viewModel.errorMessage.observe(this, { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("RegisterActivity", errorMessage)
            }
        })
    }
}
