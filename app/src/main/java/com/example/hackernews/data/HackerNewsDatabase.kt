package com.example.hackernews.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.hackernews.data.dao.StoryDao
import com.example.hackernews.data.entities.Story

@Database(entities = arrayOf(Story::class), version = 1)
@TypeConverters(Converters::class)
abstract class HackerNewsDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao

    companion object {

        private const val DATABASE_NAME = "hacker_news_db.db"

        @Volatile
        private var INSTANCE: HackerNewsDatabase? = null

        fun getDatabase(context: Context): HackerNewsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HackerNewsDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}