package com.example.kval

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class Data(
    @SerializedName("имя того что получаем из json") //менять
    @Expose
    val text: String? = null
)

interface ApiService {

    //2 варианта:
    // 1. либо .../get?q=...&... (надо писать через @Query, он сам автоматически добавит параметр например "&q=москва")
    // 2. либо /get/{q}... (надо писать через @Path)
    @GET("часть ссылки, которая отвечает за запрос")                   //менять
    fun getData(@Query("q") str: String): Call<List<Data>> //менять

    companion object {
        fun getApi(): ApiService =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("основная начальная часть ссылки") //менять
                .build()
                .create(ApiService::class.java)
    }
}