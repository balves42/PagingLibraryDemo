package com.balves42.paginglibrarydemo.mainActivity.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.balves42.paginglibrarydemo.mainActivity.MainActivityPresenter
import com.balves42.paginglibrarydemo.api.News

class NewsDataSourceFactory(
    private val mPresenter: MainActivityPresenter
) : DataSource.Factory<Int, News>() {

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, News> {
        val newsDataSource = NewsDataSource(mPresenter)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}