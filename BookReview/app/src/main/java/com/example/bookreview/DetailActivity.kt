package com.example.bookreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.bookreview.databinding.ActivityDetailBinding
import com.example.bookreview.model.Book
import com.example.bookreview.model.Review

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = getAppDataBase(this)

        val bookModel = intent.getParcelableExtra<Book>("bookInfo")
        initView(bookModel)

    }

    private fun initView(bookModel: Book?){
        Glide.with(binding.bookThumbnail.context)
            .load(bookModel?.imageLink.orEmpty())
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.bookThumbnail)

        binding.bookTitle.text = bookModel?.title.orEmpty()
        binding.descriptionTextView.text = bookModel?.desc.orEmpty()

        binding.saveButton.setOnClickListener{
            saveReview(bookModel?.id)
        }
       setReview(bookModel)
    }

    private fun setReview(bookModel: Book?){
        Thread{
            val review : Review? =
            db.reviewDao()
                .getOne(bookModel?.id.orEmpty())

            runOnUiThread {
                binding.reviewEditText.setText(review?.review.orEmpty())
            }
        }.start()
    }

    private fun saveReview(isbn: String?){
        Thread(Runnable {
            db.reviewDao()
                .saveReview(Review(isbn.orEmpty(), binding.reviewEditText.text.toString()))
        }).start()
    }
}