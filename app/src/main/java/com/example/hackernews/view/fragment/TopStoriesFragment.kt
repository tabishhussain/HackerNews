package com.example.hackernews.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackernews.R
import com.example.hackernews.data.entities.HackerNewsItem
import com.example.hackernews.databinding.FragmentTopStoriesBinding
import com.example.hackernews.view.activities.ItemDetailActivity
import com.example.hackernews.view.activities.WebViewActivity
import com.example.hackernews.view.adapters.TopStoriesAdapter
import com.example.hackernews.view.listener.RecyclerViewClickListener
import com.example.hackernews.view.viewmodel.TopStoriesViewModel

class TopStoriesFragment : Fragment(), RecyclerViewClickListener<HackerNewsItem> {

    private lateinit var topStoriesViewModel: TopStoriesViewModel
    private lateinit var binding: FragmentTopStoriesBinding
    private lateinit var adapter: TopStoriesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_stories, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topStoriesViewModel = ViewModelProviders.of(this).get(TopStoriesViewModel::class.java)
        topStoriesViewModel.fetchTopHackerNews(false)
        adapter = TopStoriesAdapter(context!!, this)
        binding.toStoriesList.layoutManager = LinearLayoutManager(context)
        binding.toStoriesList.adapter = adapter

        topStoriesViewModel.stories.observe(this,
            Observer<List<HackerNewsItem>> { stories ->
                if (stories!!.isNotEmpty()) {
                    binding.swipeToRefresh.isRefreshing = false
                    adapter.setStories(stories.reversed())
                }
            })
        binding.swipeToRefresh.setOnRefreshListener {
            topStoriesViewModel.fetchTopHackerNews(
                true
            )
        }
    }

    override fun onItemClick(obj: HackerNewsItem) {
        val intent: Intent
        if (TextUtils.isEmpty(obj.url)) {
            intent = Intent(this.context, ItemDetailActivity::class.java)
            intent.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, obj.id)
        } else {
            intent = Intent(this.context, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.EXTRA_URL, obj.url)
        }
        startActivity(intent)
    }
}