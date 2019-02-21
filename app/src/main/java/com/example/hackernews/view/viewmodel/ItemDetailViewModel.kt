package com.example.hackernews.view.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.hackernews.data.entities.HackerNewsItem
import com.example.hackernews.data.repositories.HackerNewsItemRepository

class ItemDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val hackerNewsItemRepository: HackerNewsItemRepository =
        HackerNewsItemRepository(application)

    private lateinit var comments: LiveData<List<HackerNewsItem>>
    private lateinit var hackerNewsItem: LiveData<HackerNewsItem>
    var itemId: Int = -1

    fun getComments(parentId: Int): LiveData<List<HackerNewsItem>> {
        comments = hackerNewsItemRepository.getCommentsForItem(parentId)
        return comments
    }

    fun getItemDetail(itemId: Int): LiveData<HackerNewsItem> {
        hackerNewsItem = hackerNewsItemRepository.getItemDetail(itemId)
        return hackerNewsItem
    }

}