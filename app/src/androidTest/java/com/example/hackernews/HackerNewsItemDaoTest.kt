package com.example.hackernews

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hackernews.data.HackerNewsDatabase
import com.example.hackernews.data.dao.HackerNewsItemDao
import com.example.hackernews.data.entities.HackerNewsItem
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class HackerNewsItemDaoTest {

    private lateinit var database: HackerNewsDatabase
    private lateinit var hackerNewsItemDao: HackerNewsItemDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(
            context, HackerNewsDatabase::class.java).build()
        hackerNewsItemDao = database.storyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun testHackerNewsItemInsert(){
        hackerNewsItemDao.insert(HackerNewsItem(1234, "story", 5678))
        hackerNewsItemDao.insert(HackerNewsItem(3454, "story", 98792))
        hackerNewsItemDao.insert(HackerNewsItem(19097, "story", 6517))
        hackerNewsItemDao.insert(HackerNewsItem(32443, "comment", 6517))

        val allStoriesLiveData = hackerNewsItemDao.getAllStories()
        val storiesList = getValueFromLiveData(allStoriesLiveData)
        assertEquals(3, storiesList.size)

        val storyLiveData = hackerNewsItemDao.getItemDetail(1234)
        val story = getValueFromLiveData(storyLiveData)
        assertEquals(story.type, "story")
        assertEquals(story.parent, 5678)

        val commentLiveData = hackerNewsItemDao.getCommentsForItem(6517)
        val comment = getValueFromLiveData(commentLiveData)
        assertEquals(comment[0].type, "comment")
        assertEquals(comment[0].id, 32443)
    }

    private fun <T> getValueFromLiveData(liveData: LiveData<T>): T {
        val data = mutableListOf<T>()
        val countDownLatch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(it: T?) {
                it?.let { it1 -> data.add(it1) }
                countDownLatch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        countDownLatch.await(1, TimeUnit.SECONDS)
        return data[0]
    }

}
