package com.balves42.paginglibrarydemo.api

import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    @GET("everything?q=sports&apiKey=aa67d8d98c8e4ad1b4f16dbd5f3be348")
    fun getNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Single<Response>
}