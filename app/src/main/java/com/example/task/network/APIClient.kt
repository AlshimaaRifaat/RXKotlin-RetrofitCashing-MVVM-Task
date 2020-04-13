package com.example.task.network

import com.example.task.Constants
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit


class APIClient {

   private var retrofit: Retrofit? = null

    init {
        retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
      
        @Volatile
        private var mInstance: APIClient? = null


        fun getInstance()= mInstance?: synchronized(this) {
            mInstance?:APIClient().also { mInstance=it }

        }
    }
    val api: APIInterface
        get() = retrofit!!.create<APIInterface>(APIInterface::class.java)

}