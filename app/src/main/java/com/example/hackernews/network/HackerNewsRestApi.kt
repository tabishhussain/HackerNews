package com.example.hackernews.network

import com.example.hackernews.data.entities.HackerNewsItem
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*



class HackerNewsRestApi {

    private val hackerNewsApi: HackerNewsApi
    private val baseUrl = "https://hacker-news.firebaseio.com/v0/"

    init {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val gson : Gson = GsonBuilder().registerTypeAdapter(Date::class.java, UnixDateTypeAdapter()).create()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        hackerNewsApi = retrofit.create(HackerNewsApi::class.java)
    }

    fun getTopStories(): Call<List<Int>> {
        return hackerNewsApi.getTopStories("pretty")
    }

    fun getStoryDetail(storyId: Int): Call<HackerNewsItem> {
        return hackerNewsApi.getStoryDetail(storyId, "pretty")
    }

    interface HackerNewsApi {

        @GET("topstories.json")
        fun getTopStories(@Query("print") printParam: String): Call<List<Int>>

        @GET("item/{item_id}.json")
        fun getStoryDetail(@Path("item_id") itemId: Int, @Query("print") printParam: String): Call<HackerNewsItem>

    }
}