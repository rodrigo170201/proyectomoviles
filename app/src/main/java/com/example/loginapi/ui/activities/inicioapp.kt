package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginapi.R
import com.example.loginapi.databinding.ActivityInicioappBinding
import com.example.loginapi.databinding.ActivityMainBinding
import com.example.loginapi.ui.viewmodels.MainViewModel

class inicioapp : AppCompatActivity() {
    private lateinit var binding: ActivityInicioappBinding
    val model: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInicioappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.btnGoLogIn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Agregar otros listeners si es necesario
        binding.btnGoRegister.setOnClickListener {
            val intent = Intent(this , RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoListRestaurant.setOnClickListener {
            val intent = Intent(this,RestaurantActivity::class.java)
            startActivity(intent)
        }
    }

}