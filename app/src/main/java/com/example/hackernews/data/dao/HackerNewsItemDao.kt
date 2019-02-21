package com.example.hackernews.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.hackernews.data.entities.HackerNewsItem

@Dao
interface HackerNewsItemDao {

    @Query("select * from ${HackerNewsItem.TABLE_NAME} where ${HackerNewsItem.HACKER_NEWS_ITEM_TYPE} = 'story'")
    fun getAllStories(): LiveData<List<HackerNewsItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(hackerNewsItem: HackerNewsItem)

    @Update
    fun update(hackerNewsItem: HackerNewsItem)

}