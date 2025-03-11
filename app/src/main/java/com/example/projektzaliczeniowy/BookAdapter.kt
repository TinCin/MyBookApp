package com.example.projektzaliczeniowy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projektzaliczeniowy.databinding.ItemBookBinding

class BookAdapter(
    private val bookList: List<Book>,
    private val onClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    // Tworzenie widoku dla każdego elementu
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    // Przypisanie danych do widoku
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = bookList.size

    // Klasa ViewHolder do trzymania elementów listy
    inner class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.bookTitle.text = book.title
            binding.bookImage.setImageResource(book.imageResId)

            // Obsługa kliknięcia na książkę
            itemView.setOnClickListener {
                onClick(book)
            }
        }
    }
}
