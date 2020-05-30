package com.balves42.paginglibrarydemo.api

import io.reactivex.Single

class NewsModel {

    fun getNews(pageNumber: Int, pageSize: Int): Single<Response> {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        return apiService.getNews(pageNumber, pageSize)
    }
}