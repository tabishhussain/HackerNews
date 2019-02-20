package com.example.hackernews.network

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.util.*

class UnixDateTypeAdapter : TypeAdapter<Date>() {
    override fun write(out: JsonWriter?, value: Date?) {
        if (value == null) {
            out!!.nullValue();
        } else {
            out!!.value(value.time / 1000);
        }
    }

    override fun read(reader: JsonReader?): Date? {
        return if (reader != null)
            Date(reader.nextLong() * 1000)
        else
            null
    }
}