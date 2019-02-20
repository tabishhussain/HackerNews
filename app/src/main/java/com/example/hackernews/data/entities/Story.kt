package com.example.hackernews.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.example.hackernews.data.entities.Story.Companion.TABLE_NAME
import java.util.*

@Entity(tableName = TABLE_NAME)
class Story(

    @PrimaryKey @ColumnInfo(name = STORY_ID)
    var id: Int,
    @ColumnInfo(name = STORY_BY)
    var by: String?,
    @ColumnInfo(name = STORY_DESCENDANTS)
    var descendants: Int?,
    @ColumnInfo(name = STORY_SCORE)
    var score: Int?,
    @ColumnInfo(name = STORY_TIME)
    var time: Date?,
    @ColumnInfo(name = STORY_TITLE)
    var title: String?,
    @ColumnInfo(name = STORY_TYPE)
    var type: String?,
    @ColumnInfo(name = STORY_URL)
    var url: String?,
    @Ignore
    var kids: List<Int>?

) {
    companion object {
        const val TABLE_NAME = "story_table"
        const val STORY_ID = "story_id"
        const val STORY_BY = "story_by"
        const val STORY_DESCENDANTS = "story_descendants"
        const val STORY_SCORE = "story_score"
        const val STORY_TIME = "story_time"
        const val STORY_TITLE = "story_title"
        const val STORY_TYPE = "story_type"
        const val STORY_URL = "story_url"
    }

    override fun toString(): String {
        return "Story(by='$by', descendants=$descendants, id=$id, kids=$kids, score=$score, time=$time, title='$title', type='$type', url=$url)"
    }

    constructor() : this(-1, null, -1, -1, null, null, null, null, null)

    @Ignore
    constructor(id: Int) : this(id, null, null, null, null, null, null, null, null)
}