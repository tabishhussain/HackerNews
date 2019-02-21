package com.example.hackernews.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.hackernews.data.entities.HackerNewsItem
import com.example.hackernews.repositories.HackerNewsItemRepository

class TopStoriesViewModel(application: Application) : AndroidViewModel(application) {

    val hackerNewsItemRepository: HackerNewsItemRepository = HackerNewsItemRepository(application)
    val stories: LiveData<List<HackerNewsItem>> = hackerNewsItemRepository.topStories()

    fun fetchTopHackerNews(forceRefresh : Boolean) {
        return hackerNewsItemRepository.fetchTopHackerNews(forceRefresh)
    }

    fun fetchStoryDetail(storyId: Int) {
        return hackerNewsItemRepository.fetchStoryDetail(storyId)
    }
}