package com.example.hackernews.utils

import android.content.Context
import android.content.SharedPreferences
import org.jetbrains.anko.defaultSharedPreferences

class SharedPreferenceUtils {

    companion object {

        private const val PREF_STORY_CACHE = "PREF_STORY_CACHE_TIME"

        fun setStoryCacheTime(context: Context, time: Long) {
            val sharedPreferences: SharedPreferences = context.defaultSharedPreferences
            sharedPreferences.edit().putLong(PREF_STORY_CACHE, time).apply()
        }

        fun getStoryCacheTime(context: Context): Long {
            val sharedPreferences: SharedPreferences = context.defaultSharedPreferences
            return sharedPreferences.getLong(PREF_STORY_CACHE, 0L)
        }
    }
}