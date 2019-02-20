package com.example.hackernews.network.services

import com.example.hackernews.data.entities.Story
import com.example.hackernews.network.HackerNewsRestApi
import com.example.hackernews.network.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryService(
    private val api: HackerNewsRestApi = HackerNewsRestApi()
) {
    fun fetchTopHackerNews(responseCallback: ResponseCallback<List<Int>>) {
        val call: Call<List<Int>> = api.getTopStories()
        call.enqueue(object : Callback<List<Int>> {

            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                responseCallback.onError("Call Failed: $t")
            }

            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if (response.isSuccessful) {
                    responseCallback.onSuccess(response.body())
                } else {
                    responseCallback.onError("Call Failed: ${response.errorBody()?.string()}")
                }
            }

        })
    }

    fun fetchStoryDetail(storyId: Int, responseCallback: ResponseCallback<Story>) {
        val call: Call<Story> = api.getStoryDetail(storyId)
        call.enqueue(object : Callback<Story> {

            override fun onFailure(call: Call<Story>, t: Throwable) {
                responseCallback.onError("Call Failed: $t")
            }

            override fun onResponse(call: Call<Story>, response: Response<Story>) {
                if (response.isSuccessful) {
                    responseCallback.onSuccess(response.body())
                } else {
                    responseCallback.onError("Call Failed: ${response.errorBody()?.string()}")
                }
            }

        })
    }
}