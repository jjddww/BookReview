package com.example.bookreview.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {

    private const val baseUrl = "https://openapi.naver.com"
    private const val clientId = "bnAw0Xan4R8a3sXrhAqe"
    private const val clientKey = "DfX0RluxIz"

    private val retrofitClass = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideOkHttpClient(AppIntercepter()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private fun provideOkHttpClient(intercepter: AppIntercepter) =
        OkHttpClient.Builder().run {
            addInterceptor(intercepter)
            build()
        }


    class AppIntercepter : Interceptor {
        override fun intercept(chain: Interceptor.Chain) : Response
        = with(chain) {

            val newRequest = request().newBuilder()
                .addHeader("X-Naver-Client-Id", clientId)
                .addHeader("X-Naver-Client-Secret", clientKey)
                .build()

            proceed(newRequest)
        }

    }

    val retrofitClient: BookAPI = retrofitClass.create(BookAPI::class.java)
}