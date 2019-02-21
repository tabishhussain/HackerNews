package com.example.hackernews.data.repositories

import android.arch.lifecycle.LiveData
import android.content.Context
import android.util.Log
import com.example.hackernews.data.HackerNewsDatabase
import com.example.hackernews.data.dao.HackerNewsItemDao
import com.example.hackernews.data.entities.HackerNewsItem
import com.example.hackernews.network.ResponseCallback
import com.example.hackernews.network.services.HackerNewsService
import com.example.hackernews.utils.SharedPreferenceUtils
import org.jetbrains.anko.doAsync

class HackerNewsItemRepository(private val context: Context) {

    companion object {
        private const val CACHE_DURATION = 60 * 60 * 1000 // 1 hour
        private const val TAG = "HackerNewsRepository"
    }

    private val hackerNewsService: HackerNewsService = HackerNewsService()
    private val hackerNewsDataBase: HackerNewsDatabase = HackerNewsDatabase.getDatabase(context)
    private val hackerNewsItemDao: HackerNewsItemDao
    private val topStories: LiveData<List<HackerNewsItem>>

    init {
        hackerNewsItemDao = hackerNewsDataBase.storyDao()
        topStories = hackerNewsItemDao.getAllStories()
    }

    fun getCommentsForItem(id: Int): LiveData<List<HackerNewsItem>> {
        return hackerNewsItemDao.getCommentsForItem(id)
    }

    fun getItemDetail(itemId: Int): LiveData<HackerNewsItem> {
        return hackerNewsItemDao.getItemDetail(itemId)
    }

    fun topStories(): LiveData<List<HackerNewsItem>> {
        return topStories
    }

    fun fetchTopHackerNews(forceRefresh: Boolean) {
        val cacheTime = SharedPreferenceUtils.getStoryCacheTime(context)
        if (forceRefresh || System.currentTimeMillis() - cacheTime > CACHE_DURATION) {
            hackerNewsService.fetchTopHackerNews(object : ResponseCallback<List<Int>> {

                override fun onError(error: String) {
                    Log.d(TAG, error)
                }

                override fun onSuccess(result: List<Int>?) {
                    Log.d(TAG, result.toString())
                    if (result!!.isNotEmpty()) {
                        insertStories(result)
                        SharedPreferenceUtils.setStoryCacheTime(context, System.currentTimeMillis())
                    }
                }
            })
        }
    }

    fun fetchStoryDetail(storyId: Int) {
        hackerNewsService.fetchStoryDetail(storyId, object : ResponseCallback<HackerNewsItem> {

            override fun onError(error: String) {
                Log.d(TAG, error)
            }

            override fun onSuccess(result: HackerNewsItem?) {
                if (result != null) {
                    insertStoryDetail(result)
                }
            }

        })
    }

    fun insertStories(storyIds: List<Int>) {
        doAsync {
            storyIds.forEach {
                hackerNewsItemDao.insert(HackerNewsItem(it, "story", null))
            }
        }
    }

    fun insertStoryDetail(hackerNewsItem: HackerNewsItem) {
        doAsync {
            hackerNewsItem.kidCount = hackerNewsItem.kids?.size
            hackerNewsItemDao.update(hackerNewsItem)
            hackerNewsItem.kids?.forEach {
                hackerNewsItemDao.insert(HackerNewsItem(it, "comment", hackerNewsItem.id))
            }
        }
    }
}