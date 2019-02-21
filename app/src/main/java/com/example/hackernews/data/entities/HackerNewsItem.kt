package com.example.hackernews.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.example.hackernews.data.entities.HackerNewsItem.Companion.TABLE_NAME
import java.util.*

@Entity(tableName = TABLE_NAME)
class HackerNewsItem(

    @PrimaryKey @ColumnInfo(name = HACKER_NEWS_ITEM_ID)
    var id: Int,
    @ColumnInfo(name = HACKER_NEWS_ITEM_BY)
    var by: String?,
    @ColumnInfo(name = HACKER_NEWS_ITEM_DESCENDANTS)
    var descendants: Int?,
    @ColumnInfo(name = HACKER_NEWS_ITEM_SCORE)
    var score: Int?,
    @ColumnInfo(name = HACKER_NEWS_ITEM_TIME)
    var time: Date?,
    @ColumnInfo(name = HACKER_NEWS_ITEM_TITLE)
    var title: String?,
    @ColumnInfo(name = HACKER_NEWS_ITEM_TYPE)
    var type: String?,
    @ColumnInfo(name = HACKER_NEWS_ITEM_URL)
    var url: String?,
    @ColumnInfo(name = HACKER_NEWS_ITEM_PARENT)
    var parent: Int?,
    @ColumnInfo(name = HACKER_NEWS_ITEM_TEXT)
    var text: String?,
    @Ignore
    var kids: List<Int>?

) {
    companion object {
        const val TABLE_NAME = "hacker_news_item_table"
        const val HACKER_NEWS_ITEM_ID = "hacker_news_item_id"
        const val HACKER_NEWS_ITEM_BY = "hacker_news_item_by"
        const val HACKER_NEWS_ITEM_DESCENDANTS = "hacker_news_item_descendants"
        const val HACKER_NEWS_ITEM_SCORE = "hacker_news_item_score"
        const val HACKER_NEWS_ITEM_TIME = "hacker_news_item_time"
        const val HACKER_NEWS_ITEM_TITLE = "hacker_news_item_title"
        const val HACKER_NEWS_ITEM_TYPE = "hacker_news_item_type"
        const val HACKER_NEWS_ITEM_URL = "hacker_news_item_url"
        const val HACKER_NEWS_ITEM_PARENT = "hacker_news_item_parent"
        const val HACKER_NEWS_ITEM_TEXT = "hacker_news_item_text"
    }

    override fun toString(): String {
        return "HackerNewsItem(by='$by', descendants=$descendants, id=$id, kids=$kids, score=$score, time=$time, title='$title', type='$type', url=$url)"
    }

    constructor() : this(-1, null, -1, -1, null, null, null, null, null, null, null)

    @Ignore
    constructor(id: Int, type: String, parent: Int?) : this(id, null, null, null, null, null, type, null, parent, null, null)
}