package com.example.hackernews.view.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.hackernews.data.entities.HackerNewsItem
import com.example.hackernews.data.repositories.HackerNewsItemRepository

class TopStoriesViewModel(application: Application) : AndroidViewModel(application) {

    private val hackerNewsItemRepository: HackerNewsItemRepository =
        HackerNewsItemRepository(application)
    val stories: LiveData<List<HackerNewsItem>> = hackerNewsItemRepository.topStories()

    fun fetchTopHackerNews(forceRefresh: Boolean) {
        return hackerNewsItemRepository.fetchTopHackerNews(forceRefresh)
    }

    fun fetchStoryDetail(storyId: Int) {
        return hackerNewsItemRepository.fetchStoryDetail(storyId)
    }

    fun isLoading(): MutableLiveData<Boolean> {
        return hackerNewsItemRepository.isLoading
    }
}