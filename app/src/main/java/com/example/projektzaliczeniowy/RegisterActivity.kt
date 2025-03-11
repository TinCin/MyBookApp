package com.example.projektzaliczeniowy

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projektzaliczeniowy.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    // Powiązanie widoku (View Binding)
    private lateinit var binding: ActivityRegisterBinding

    // Firebase Authentication
    private lateinit var firebaseAuth: FirebaseAuth

    // Okno dialogowe postępu
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicjalizacja Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Inicjalizacja okna dialogowego postępu, które będzie widoczne podczas rejestracji
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Proszę czekać")
        progressDialog.setCanceledOnTouchOutside(false)

        // Obsługa kliknięcia przycisku "Wróć"
        binding.backBtn.setOnClickListener {
            onBackPressed() // Powrót do poprzedniego ekranu
        }

        // Obsługa kliknięcia przycisku "Rejestruj"
        binding.registerBtn.setOnClickListener {
            /* Kroki rejestracji:
            1) Wprowadzenie danych
            2) Walidacja danych
            3) Utworzenie konta - Firebase Auth
            4) Zapisanie informacji użytkownika - Firebase Database
             */
            validateData()
        }
    }

    private var name = ""
    private var email = ""
    private var password = ""

    private fun validateData() {
        // 1) Pobranie danych wprowadzonych przez użytkownika
        name = binding.nameEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()
        val cPassword = binding.cpasswordEt.text.toString().trim()

        // 2) Walidacja danych
        if (name.isEmpty()) {
            // Pole "imię" jest puste
            Toast.makeText(this, "Wprowadź swoje imię...", Toast.LENGTH_SHORT).show()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Nieprawidłowy format e-maila
            Toast.makeText(this, "Nieprawidłowy format e-maila...", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            // Pole "hasło" jest puste
            Toast.makeText(this, "Wprowadź hasło...", Toast.LENGTH_SHORT).show()
        } else if (cPassword.isEmpty()) {
            // Pole "potwierdź hasło" jest puste
            Toast.makeText(this, "Potwierdź hasło...", Toast.LENGTH_SHORT).show()
        } else if (password != cPassword) {
            // Hasła nie są zgodne
            Toast.makeText(this, "Hasła nie są zgodne...", Toast.LENGTH_SHORT).show()
        } else {
            // Wszystkie dane są poprawne, rozpoczęcie tworzenia konta
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        // 3) Utworzenie konta w Firebase Auth

        // Wyświetlenie okna dialogowego postępu
        progressDialog.setMessage("Tworzenie konta...")
        progressDialog.show()

        // Utworzenie użytkownika w Firebase Auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Konto utworzone, zapisanie informacji użytkownika w bazie danych
                updateUserInfo()
            }
            .addOnFailureListener { e ->
                // Nie udało się utworzyć konta
                progressDialog.dismiss()
                Toast.makeText(this, "Nie udało się utworzyć konta: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo() {
        // 4) Zapisanie informacji użytkownika w Firebase Realtime Database

        progressDialog.setMessage("Zapisywanie danych użytkownika...")

        // Znacznik czasu
        val timestamp = System.currentTimeMillis()

        // Pobranie identyfikatora UID bieżącego użytkownika (dostępne po rejestracji)
        val uid = firebaseAuth.uid

        // Przygotowanie danych do zapisania w bazie
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["profileImage"] = "" // Puste pole obrazu profilowego, zostanie dodane później
        hashMap["userType"] = "user" // Możliwe wartości: "user"/"admin", wartość "admin" można zmienić ręcznie w Firebase
        hashMap["timestamp"] = timestamp

        // Zapisanie danych w bazie Firebase
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                // Dane użytkownika zapisane, przejście do ekranu głównego użytkownika
                progressDialog.dismiss()
                Toast.makeText(this, "Konto zostało utworzone...", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, DashboardUserActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                // Nie udało się zapisać danych w bazie
                progressDialog.dismiss()
                Toast.makeText(this, "Nie udało się zapisać danych użytkownika: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
