package com.example.bookreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.bookreview.adapter.BookListAdapter
import com.example.bookreview.adapter.HistoryAdapter
import com.example.bookreview.api.RetrofitClass
import com.example.bookreview.databinding.ActivityMainBinding
import com.example.bookreview.model.History
import com.example.bookreview.model.SearchBookDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var db: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = getAppDataBase(this)

        bookListAdapter = BookListAdapter(clickListener = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("bookInfo", it)
            startActivity(intent)
        })
        binding.bookList.adapter = bookListAdapter
        binding.bookList.layoutManager = LinearLayoutManager(this)

        historyAdapter = HistoryAdapter(historyDeleteClickListener = {
            deleteSearchKeyword(it)
        })
        binding.searchHistory.adapter = historyAdapter
        binding.searchHistory.layoutManager = LinearLayoutManager(this)


        binding.searchEditText.setOnKeyListener{ _, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN){
                showHistoryView()
                searchBookByName(binding.searchEditText.text.toString(), 20)
                return@setOnKeyListener true //처리 완료
            }
            return@setOnKeyListener false //처리 미완료
        }

        binding.searchEditText.setOnTouchListener{ _, event ->
            if(event.action == MotionEvent.ACTION_DOWN)
                showHistoryView()
            return@setOnTouchListener false
        }
    }

    private fun searchBookByName(keyWord : String, itemCount: Int) {
        RetrofitClass.retrofitClient.getBookList(keyWord, itemCount).enqueue(object : Callback<SearchBookDto>{
            override fun onResponse(call: Call<SearchBookDto>, response: Response<SearchBookDto>) {
                saveSearchKeyword(keyWord)
                hideHistoryView()

                if(response.isSuccessful.not()){
                    return
                }
                else{
                    bookListAdapter.submitList(response.body()?.bookList.orEmpty())
                }
            }

            override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                t.printStackTrace()
                hideHistoryView()
            }

        })
    }

    private fun saveSearchKeyword(keyword: String?) {
        Thread(Runnable {
            db.historyDao().insertHistory(History(null, keyword))
        }).start()
    }

    private fun deleteSearchKeyword(keyword: String?) {
        Thread(Runnable {
            db.historyDao().deleteHistory(keyword)
            showHistoryView()
        }).start()
    }

    private fun showHistoryView(){

        Thread(Runnable {
            db.historyDao()
                .getAll()
                .reversed()
                .run {
                runOnUiThread{
                    binding.searchHistory.isVisible = true
                    historyAdapter.submitList(this)
                }
            }
        }).start()
    }

    private fun hideHistoryView(){
        binding.searchHistory.isVisible = false
    }
}