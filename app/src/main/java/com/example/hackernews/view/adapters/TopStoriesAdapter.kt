package com.example.hackernews.view.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackernews.R
import com.example.hackernews.data.entities.Story
import com.example.hackernews.databinding.ItemStoriesListBinding
import com.example.hackernews.repositories.StoryRepository

class TopStoriesAdapter(context: Context) : RecyclerView.Adapter<TopStoriesAdapter.TopStoriesViewHolder>() {

    private var stories = emptyList<Story>()
    private val storyRepository: StoryRepository = StoryRepository(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopStoriesViewHolder {
        return TopStoriesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_stories_list, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onBindViewHolder(holder: TopStoriesViewHolder, postion: Int) {
        val story = stories.get(postion)
        if (!TextUtils.isEmpty(story.title)) {
            holder.binding.title.text = story.title
            holder.binding.progressBar.visibility = View.INVISIBLE
        } else {
            storyRepository.fetchStoryDetail(story.id)
            holder.binding.title.text = "Loading..."
            holder.binding.progressBar.visibility = View.VISIBLE
        }
    }

    inner class TopStoriesViewHolder(val binding: ItemStoriesListBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    fun setStories(stories: List<Story>) {
        this.stories = stories
        notifyDataSetChanged()
    }
}