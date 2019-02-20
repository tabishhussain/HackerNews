package com.example.hackernews.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.hackernews.data.entities.Story
import com.example.hackernews.repositories.StoryRepository

class TopStoriesViewModel(application: Application) : AndroidViewModel(application) {

    val storyRepository: StoryRepository = StoryRepository(application)
    val stories: LiveData<List<Story>> = storyRepository.topStories()

    fun fetchTopHackerNews() {
        return storyRepository.fetchTopHackerNews()
    }

    fun fetchStoryDetail(storyId: Int) {
        return storyRepository.fetchStoryDetail(storyId)
    }
}