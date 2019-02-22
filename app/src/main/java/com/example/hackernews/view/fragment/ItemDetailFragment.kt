package com.example.hackernews.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackernews.R
import com.example.hackernews.data.entities.HackerNewsItem
import com.example.hackernews.databinding.FragmentItemDetailBinding
import com.example.hackernews.utils.CommonUtils
import com.example.hackernews.view.activities.ItemDetailActivity
import com.example.hackernews.view.adapters.CommentsAdapter
import com.example.hackernews.view.listener.RecyclerViewClickListener
import com.example.hackernews.view.viewmodel.ItemDetailViewModel

class ItemDetailFragment : Fragment(), RecyclerViewClickListener<HackerNewsItem> {

    private lateinit var itemDetailViewModel: ItemDetailViewModel
    private lateinit var binding: FragmentItemDetailBinding
    private lateinit var adapter: CommentsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(this.context),
                R.layout.fragment_item_detail, container, false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemDetailViewModel = ViewModelProviders.of(this).get(ItemDetailViewModel::class.java)
        itemDetailViewModel.itemId = arguments?.getInt(ItemDetailActivity.EXTRA_ITEM_ID) ?: -1
        adapter = CommentsAdapter(context!!, this)
        binding.commentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.commentRecyclerView.adapter = adapter
        if (itemDetailViewModel.itemId != -1) {
            itemDetailViewModel.getItemDetail(itemDetailViewModel.itemId)
                .observe(this,
                    Observer<HackerNewsItem> { hackerNewItem ->
                        if (hackerNewItem != null) {
                            bindItem(hackerNewItem)

                        }
                    })

            itemDetailViewModel.getComments(itemDetailViewModel.itemId).observe(this, Observer { comments ->
                val notEmpty = comments?.isNotEmpty()!!
                adapter.setComment(comments.reversed())
                binding.commentRecyclerView.visibility = if (notEmpty) View.VISIBLE else View.GONE
                binding.emptyText.visibility = if (notEmpty) View.GONE else View.VISIBLE
            })
        }
    }

    private fun bindItem(hackerNewItem: HackerNewsItem) {
        if ("story".equals(hackerNewItem.type, true)) {
            binding.title.text = hackerNewItem.title
            binding.score.text = hackerNewItem.score.toString()
            binding.labelComment.text = getString(R.string.label_comments)
        } else{
            binding.labelComment.text = getString(R.string.label_replies)
            binding.score.visibility = View.INVISIBLE
            if (Build.VERSION.SDK_INT >= 24) {
                binding.title.text = Html.fromHtml(hackerNewItem.text, Html.FROM_HTML_MODE_LEGACY)
            } else {
                binding.title.text = Html.fromHtml(hackerNewItem.text)
            }
            binding.title.movementMethod = LinkMovementMethod.getInstance()
            binding.title.linksClickable = false
        }
        binding.info.text =
            String.format("${hackerNewItem.time?.let { CommonUtils.getTimeAgo(it) }} | by ${hackerNewItem.by}")
    }

    override fun onItemClick(obj: HackerNewsItem) {
        val intent = Intent(this.context, ItemDetailActivity::class.java)
        intent.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, obj.id)
        startActivity(intent)
    }

}