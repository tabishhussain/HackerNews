package com.example.hackernews.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.hackernews.data.entities.Story

@Dao
interface StoryDao {

    @Query("select * from ${Story.TABLE_NAME}")
    fun getAllStories(): LiveData<List<Story>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(story: Story)

    @Update
    fun update(story: Story)

}