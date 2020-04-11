package com.example.task.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit


class APIClient {


    companion object {
        private val BasuRl: String = "https://api.themoviedb.org/3/"

        private var retrofit: Retrofit? = null
        public fun getClient(): Retrofit? {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BasuRl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
            }

            return retrofit
        }
    }
}