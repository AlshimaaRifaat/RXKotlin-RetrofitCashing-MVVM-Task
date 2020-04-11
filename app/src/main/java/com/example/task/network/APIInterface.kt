package com.example.task.network

import com.example.task.model.PopularPeopleModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("person/popular")
    fun popularPeople_List(@Query("api_key") api_key: String,
                           @Query("language") language: String,
                           @Query("page") page: Int): Call<PopularPeopleModel>
}