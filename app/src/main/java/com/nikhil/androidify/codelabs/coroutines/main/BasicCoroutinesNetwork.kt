package com.nikhil.androidify.codelabs.coroutines.main

import com.nikhil.androidify.codelabs.coroutines.util.SkipNetworkInterceptor
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val service: BasicCoroutinesNetwork by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(SkipNetworkInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(BasicCoroutinesNetwork::class.java)
}

fun getNetworkService() = service

/**
 * Main network interface which will fetch a new welcome title for us
 *
 * If you still wanted to provide access to [Retrofit]'s full [Result], you can return [Result<String>] instead of [String] from the `suspend` function.
 *
 * [Retrofit] will automatically make [suspend] functions **main-safe** so you can call them directly from [Dispatchers.Main].
 */
interface BasicCoroutinesNetwork {
    @GET("next_title.json")
    suspend fun fetchNextTitle(): String
}