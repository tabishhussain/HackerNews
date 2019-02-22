package com.example.hackernews.view.activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.hackernews.R
import com.example.hackernews.data.entities.HackerNewsItem
import com.example.hackernews.data.repositories.HackerNewsItemRepository

class SplashScreenActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val hackerNewsItemRepository = HackerNewsItemRepository(this)
        hackerNewsItemRepository.fetchTopHackerNews(false)
        hackerNewsItemRepository.topStories().observe(this, object : Observer<List<HackerNewsItem>>{
            override fun onChanged(t: List<HackerNewsItem>?) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                hackerNewsItemRepository.topStories().removeObserver(this)
            }
        })

    }
}
