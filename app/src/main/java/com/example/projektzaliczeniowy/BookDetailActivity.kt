package com.example.projektzaliczeniowy

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookDetailActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Pobierz dane o książce
        val title = intent.getStringExtra("BOOK_TITLE")
        val description = intent.getStringExtra("BOOK_DESCRIPTION")
        val imageResId = intent.getIntExtra("BOOK_IMAGE", 0)

        // Ustaw dane w widokach
        val titleTextView: TextView = findViewById(R.id.bookTitle)
        val descriptionTextView: TextView = findViewById(R.id.bookDescription)
        val bookImageView: ImageView = findViewById(R.id.bookImageView)

        titleTextView.text = title
        descriptionTextView.text = description
        bookImageView.setImageResource(imageResId)
    }
}
