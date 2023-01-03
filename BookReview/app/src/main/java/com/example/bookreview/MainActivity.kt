package com.example.bookreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookreview.adapter.BookListAdapter
import com.example.bookreview.api.BookAPI
import com.example.bookreview.api.RetrofitClass
import com.example.bookreview.databinding.ActivityMainBinding
import com.example.bookreview.model.SearchBookDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofitClass: BookAPI
    private lateinit var bookListAdapter: BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofitClass = RetrofitClass.retrofitClient

        bookListAdapter = BookListAdapter()
        binding.bookList.adapter = bookListAdapter
        binding.bookList.layoutManager = LinearLayoutManager(this)

        retrofitClass.getBookList("DDD").enqueue(object : Callback<SearchBookDto>{
            override fun onResponse(call: Call<SearchBookDto>, response: Response<SearchBookDto>) {

                if(response.isSuccessful.not()){
                    return
                }
                else{
                    bookListAdapter.submitList(response.body()?.bookList)
                }
            }

            override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}