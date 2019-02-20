package com.example.hackernews.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.hackernews.R
import com.example.hackernews.view.fragment.TopStoriesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.content, TopStoriesFragment()).commit()
    }
}
