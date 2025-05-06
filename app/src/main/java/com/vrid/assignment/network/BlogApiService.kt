package com.vrid.assignment.network

import com.vrid.assignment.BuildConfig
import com.vrid.assignment.data_models.BlogPost
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(20, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .writeTimeout(20, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val blogService: BlogApiService = retrofit.create(BlogApiService::class.java)

interface BlogApiService {
    @GET("wp-json/wp/v2/posts?per_page=10&page=1")
    suspend fun getBlogPosts(): List<BlogPost>
}