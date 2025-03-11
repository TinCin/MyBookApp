package com.example.projektzaliczeniowy

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projektzaliczeniowy.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    // Powiązanie widoku (View Binding)
    private lateinit var binding: ActivityLoginBinding

    // Firebase Auth - uwierzytelnianie użytkowników
    private lateinit var firebaseAuth: FirebaseAuth

    // Okno dialogowe postępu
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicjalizacja Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Inicjalizacja okna dialogowego postępu, wyświetlanego podczas logowania
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Proszę czekać")
        progressDialog.setCanceledOnTouchOutside(false)

        // Obsługa kliknięcia: brak konta - przejście do ekranu rejestracji
        binding.noAccountTv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Obsługa kliknięcia: rozpoczęcie logowania
        binding.loginBtn.setOnClickListener {
            /* Kroki:
            1) Wprowadzenie danych
            2) Walidacja danych
            3) Logowanie - Firebase Auth
            4) Sprawdzenie typu użytkownika - Firebase Auth
                jeśli użytkownik - przejście do panelu użytkownika
                jeśli administrator - przejście do panelu administratora
             */
            validateData()
        }
    }

    // Zmienne przechowujące dane logowania
    private var email = ""
    private var password = ""

    private fun validateData() {
        // 1) Wprowadzenie danych
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        // 2) Walidacja danych
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Nieprawidłowy format e-maila...", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Wprowadź hasło...", Toast.LENGTH_SHORT).show()
        } else {
            loginUser()
        }
    }

    private fun loginUser() {
        // 3) Logowanie - Firebase Auth

        // Wyświetlenie okna postępu
        progressDialog.setMessage("Logowanie...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Logowanie zakończone sukcesem
                checkUser()
            }
            .addOnFailureListener { e ->
                // Logowanie nieudane
                progressDialog.dismiss()
                Toast.makeText(this, "Logowanie nieudane: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        /* 4) Sprawdzenie typu użytkownika - Firebase Auth
        - Jeśli użytkownik - przejście do panelu użytkownika
        - Jeśli administrator - przejście do panelu administratora
         */
        progressDialog.setMessage("Sprawdzanie użytkownika...")

        val firebaseUser = firebaseAuth.currentUser!!

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()

                    // Pobranie typu użytkownika, np. "user" lub "admin"
                    val userType = snapshot.child("userType").value
                    if (userType == "user") {
                        // Zwykły użytkownik - otwarcie panelu użytkownika
                        startActivity(Intent(this@LoginActivity, DashboardUserActivity::class.java))
                        finish()
                    } else if (userType == "admin") {
                        // Administrator - otwarcie panelu administratora
                        startActivity(Intent(this@LoginActivity, DashboardAdminActivity::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Obsługa błędu pobierania danych z bazy
                }
            })
    }
}
