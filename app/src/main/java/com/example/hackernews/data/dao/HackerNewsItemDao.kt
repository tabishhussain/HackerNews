package com.example.hackernews.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.hackernews.data.entities.HackerNewsItem

@Dao
interface HackerNewsItemDao {

    @Query("select * from ${HackerNewsItem.TABLE_NAME} where ${HackerNewsItem.HACKER_NEWS_ITEM_TYPE} = 'story'")
    fun getAllStories(): LiveData<List<HackerNewsItem>>

    @Query("select * from ${HackerNewsItem.TABLE_NAME} where ${HackerNewsItem.HACKER_NEWS_ITEM_ID} = :itemId")
    fun getItemDetail(itemId: Int): LiveData<HackerNewsItem>

    @Query("select * from ${HackerNewsItem.TABLE_NAME} where ${HackerNewsItem.HACKER_NEWS_ITEM_TYPE} = 'comment' and ${HackerNewsItem.HACKER_NEWS_ITEM_PARENT} = :parentId")
    fun getCommentsForItem(parentId: Int): LiveData<List<HackerNewsItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(hackerNewsItem: HackerNewsItem)

    @Update
    fun update(hackerNewsItem: HackerNewsItem)

}