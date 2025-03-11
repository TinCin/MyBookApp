package com.example.projektzaliczeniowy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashActivity : AppCompatActivity() {

    // Deklaracja obiektu FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Ustawienie nowoczesnego trybu wizualnego z efektami edge-to-edge
        setContentView(R.layout.activity_splash) // Ustawienie widoku dla ekranu startowego (Splash)

        // Inicjalizacja FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Oczekiwanie przez 1 sekundę, a następnie sprawdzanie statusu użytkownika
        Handler().postDelayed({
            checkUser()
        }, 1000)
    }

    private fun checkUser() {
        // Pobranie bieżącego użytkownika (sprawdzenie, czy jest zalogowany)
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            // Użytkownik nie jest zalogowany, przejście do ekranu głównego (MainActivity)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            // Użytkownik jest zalogowany, sprawdzenie jego typu (user/admin)

            val ref = FirebaseDatabase.getInstance().getReference("Users")
            ref.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        // Pobranie typu użytkownika z bazy danych (user lub admin)
                        val userType = snapshot.child("userType").value
                        if (userType == "user") {
                            // Zwykły użytkownik, przejście do panelu użytkownika (DashboardUserActivity)
                            startActivity(Intent(this@SplashActivity, DashboardUserActivity::class.java))
                            finish()
                        } else if (userType == "admin") {
                            // Administrator, przejście do panelu administratora (DashboardAdminActivity)
                            startActivity(Intent(this@SplashActivity, DashboardAdminActivity::class.java))
                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Obsługa błędów, które mogą wystąpić podczas komunikacji z bazą danych
                    }
                })
        }
    }
}
