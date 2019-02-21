package com.example.hackernews.view.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackernews.R
import com.example.hackernews.data.entities.HackerNewsItem
import com.example.hackernews.data.repositories.HackerNewsItemRepository
import com.example.hackernews.databinding.ItemStoriesListBinding
import com.example.hackernews.utils.CommonUtils
import com.example.hackernews.view.listener.RecyclerViewClickListener

class TopStoriesAdapter(
    private val context: Context,
    private val recyclerClickListener: RecyclerViewClickListener<HackerNewsItem>
) :
    RecyclerView.Adapter<TopStoriesAdapter.TopStoriesViewHolder>() {

    private var stories = emptyList<HackerNewsItem>()
    private val hackerNewsItemRepository: HackerNewsItemRepository =
        HackerNewsItemRepository(context)
    private var alreadyRequestedItem: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoriesViewHolder {
        return TopStoriesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_stories_list, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onBindViewHolder(holder: TopStoriesViewHolder, postion: Int) {
        val story = stories[postion]
        if (!TextUtils.isEmpty(story.title)) {
            if (TextUtils.isEmpty(story.url)) {
                Log.d("StoryWithoutUrl", story.title + " " + story.kidCount)
            }
            holder.binding.title.text = story.title
            holder.binding.score.text = story.score.toString()
            val comments = if (story.kidCount == null) 0 else story.kidCount
            holder.binding.info.text =
                String.format("$comments Comments | ${story.time?.let { CommonUtils.getTimeAgo(it) }} | by ${story.by}")
            holder.binding.progressBar.visibility = View.INVISIBLE
            holder.binding.root.setOnClickListener { recyclerClickListener.onItemClick(story) }
        } else if (!alreadyRequestedItem.contains(story.id)) {
            holder.binding.root.setOnClickListener(null)
            hackerNewsItemRepository.fetchStoryDetail(story.id)
            holder.binding.title.text = context.getString(R.string.string_loading)
            holder.binding.progressBar.visibility = View.VISIBLE
            alreadyRequestedItem.add(story.id)
        }
    }

    inner class TopStoriesViewHolder(val binding: ItemStoriesListBinding) : RecyclerView.ViewHolder(binding.root)

    fun setStories(hackerNewsItems: List<HackerNewsItem>) {
        this.stories = hackerNewsItems
        notifyDataSetChanged()
    }
}