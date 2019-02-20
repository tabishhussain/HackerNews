package com.example.hackernews.repositories

import android.arch.lifecycle.LiveData
import android.content.Context
import android.util.Log
import com.example.hackernews.data.HackerNewsDatabase
import com.example.hackernews.data.dao.StoryDao
import com.example.hackernews.data.entities.Story
import com.example.hackernews.network.ResponseCallback
import com.example.hackernews.network.services.StoryService
import org.jetbrains.anko.doAsync

class StoryRepository(context: Context) {

    private val tag: String = "StoryRepository"
    private val storyService: StoryService =
        StoryService()
    private val hackerNewsDataBase: HackerNewsDatabase = HackerNewsDatabase.getDatabase(context)
    private val storyDao: StoryDao
    private val topStories: LiveData<List<Story>>

    init {
        storyDao = hackerNewsDataBase.storyDao()
        topStories = storyDao.getAllStories()
    }

    fun topStories(): LiveData<List<Story>> {
        return topStories
    }

    fun fetchTopHackerNews() {
        storyService.fetchTopHackerNews(object : ResponseCallback<List<Int>> {

            override fun onError(error: String) {
                Log.d(tag, error)
            }

            override fun onSuccess(result: List<Int>?) {
                Log.d(tag, result.toString())
                if (result!!.isNotEmpty()) {
                    insertStories(result)
                }
            }
        })
    }

    fun fetchStoryDetail(storyId: Int) {
        storyService.fetchStoryDetail(storyId, object : ResponseCallback<Story> {

            override fun onError(error: String) {
                Log.d(tag, error)
            }

            override fun onSuccess(result: Story?) {
                if (result != null) {
                    insertStoryDetail(result)
                }
            }

        })
    }

    fun insertStories(storyIds: List<Int>) {
        doAsync {
            for (id in storyIds) {
                storyDao.insert(Story(id))
            }
        }
    }

    fun insertStoryDetail(story: Story) {
        doAsync {
            storyDao.update(story)

        }
    }
}