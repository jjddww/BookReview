package com.example.bookreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookreview.R
import com.example.bookreview.databinding.ItemBookBinding
import com.example.bookreview.model.Book

class BookListAdapter() : ListAdapter<Book, BookListAdapter.BookItemViewHolder> (diffUtil){

    inner class BookItemViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(bookModel: Book){
            binding.bookTitle.text = bookModel.title
            binding.authorName.text = bookModel.author
            Glide.with(binding.bookThumbnail.context)
                .load(bookModel.imageLink)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.bookThumbnail)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Book> () {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem== newItem
            }

        }
    }
}
