package com.example.hackernews.network

interface ResponseCallback<T> {

    fun onError(error: String)

    fun onSuccess(result: T?)
}