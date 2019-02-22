package com.example.hackernews.view.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackernews.R
import com.example.hackernews.data.entities.HackerNewsItem
import com.example.hackernews.data.repositories.HackerNewsItemRepository
import com.example.hackernews.databinding.ItemCommentListBinding
import com.example.hackernews.utils.CommonUtils
import com.example.hackernews.view.listener.RecyclerViewClickListener

class CommentsAdapter(
    private val context: Context,
    private val recyclerClickListener: RecyclerViewClickListener<HackerNewsItem>
) :
    RecyclerView.Adapter<CommentsAdapter.CommentHolder>() {

    private var comments = emptyList<HackerNewsItem>()
    private val hackerNewsItemRepository: HackerNewsItemRepository =
        HackerNewsItemRepository(context)
    private var alreadyRequestedItem: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        return CommentHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_comment_list, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentHolder, postion: Int) {
        val comment = comments[postion]
        if (!TextUtils.isEmpty(comment.text)) {
            if (Build.VERSION.SDK_INT >= 24) {
                holder.binding.title.text = Html.fromHtml(comment.text, Html.FROM_HTML_MODE_LEGACY)
            } else {
                holder.binding.title.text = Html.fromHtml(comment.text)
            }
            holder.binding.title.movementMethod = LinkMovementMethod.getInstance()
            holder.binding.title.linksClickable = false
            holder.binding.score.visibility = View.INVISIBLE
            holder.binding.info.text =
                String.format("${comment.time?.let { CommonUtils.getTimeAgo(it) }} | by ${comment.by}")
            holder.binding.progressBar.visibility = View.INVISIBLE
            val replies = if (comment.kidCount == null) 0 else comment.kidCount
            holder.binding.viewReply.setOnClickListener { recyclerClickListener.onItemClick(comment) }
            setViewVisibilities(holder.binding, false)
            holder.binding.viewReply.visibility = if (replies == 0) View.GONE else View.VISIBLE
        } else if (!alreadyRequestedItem.contains(comment.id)) {
            hackerNewsItemRepository.fetchStoryDetail(comment.id)
            alreadyRequestedItem.add(comment.id)
            holder.binding.root.setOnClickListener(null)
            holder.binding.title.text = context.getString(R.string.string_loading)
            setViewVisibilities(holder.binding, true)
        }
    }

    private fun setViewVisibilities(binding: ItemCommentListBinding, loading: Boolean) {
        if (loading) {
            binding.score.visibility = View.INVISIBLE
            binding.info.visibility = View.INVISIBLE
            binding.viewReply.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.score.visibility = View.VISIBLE
            binding.info.visibility = View.VISIBLE
            binding.viewReply.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    inner class CommentHolder(val binding: ItemCommentListBinding) : RecyclerView.ViewHolder(binding.root)

    fun setComment(hackerNewsItems: List<HackerNewsItem>) {
        this.comments = hackerNewsItems
        notifyDataSetChanged()
    }
}