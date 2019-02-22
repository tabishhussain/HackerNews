package com.example.hackernews

import com.example.hackernews.utils.CommonUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CommonUtilTest {
    @Test
    fun getTimeAgo_inFuture() {
        val calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val date = Date(calendar.time.time * 1000)
        assertEquals(CommonUtils.getTimeAgo(date), "in the future")
    }

    @Test
    fun getTimeAgo_yesterday() {
        val calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val date = Date(calendar.time.time * 1000)
        assertEquals(CommonUtils.getTimeAgo(date), "yesterday")
    }


    @Test
    fun getTimeAgo_dayAgo() {
        val calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2)
        val date = Date(calendar.time.time * 1000)
        assertEquals(CommonUtils.getTimeAgo(date), "2 days ago")
    }

    @Test
    fun getTimeAgo_hourAgo() {
        val calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -2)
        val date = Date(calendar.time.time * 1000)
        assertEquals(CommonUtils.getTimeAgo(date), "2 hours ago")
    }

    @Test
    fun getTimeAgo_minAgo() {
        val calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -2)
        val date = Date(calendar.time.time * 1000)
        assertEquals(CommonUtils.getTimeAgo(date), "2 minutes ago")
    }

}
