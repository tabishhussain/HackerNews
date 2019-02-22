package com.example.hackernews.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.hackernews.R
import com.example.hackernews.view.fragment.ItemDetailFragment

class ItemDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ITEM_ID = "extra_item_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val itemDetailFragment = ItemDetailFragment()
        itemDetailFragment.arguments = intent.extras
        supportFragmentManager.beginTransaction().replace(R.id.content, itemDetailFragment).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}
