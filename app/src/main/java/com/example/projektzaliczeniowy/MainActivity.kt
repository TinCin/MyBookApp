package com.example.projektzaliczeniowy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projektzaliczeniowy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Powiązanie widoku (View Binding)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obsługa kliknięcia: przejście do ekranu logowania
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Obsługa kliknięcia: przejście do ekranu rejestracji
        binding.SingUpButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
