package com.balves42.paginglibrarydemo.mainActivity.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.balves42.paginglibrarydemo.api.News
import com.balves42.paginglibrarydemo.mainActivity.MainActivityPresenter
import com.balves42.paginglibrarydemo.mainActivity.repositories.NewsDataSource
import com.balves42.paginglibrarydemo.mainActivity.repositories.NewsDataSourceFactory
import com.balves42.paginglibrarydemo.mainActivity.utils.State
import io.reactivex.functions.Action

class NewsViewModel(
    mPresenter: MainActivityPresenter
) : ViewModel() {
    var newsLiveData: LiveData<PagedList<News>>

    private val newsDataSourceFactory: NewsDataSourceFactory =
        NewsDataSourceFactory(mPresenter)

    init {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(10) //Page size of the first request
            .setPageSize(10) //Page size of the following requests
            .setEnablePlaceholders(false)
            .build()
        newsLiveData = LivePagedListBuilder(newsDataSourceFactory, config).build()
    }

    fun getNews(): LiveData<PagedList<News>> = newsLiveData

    fun getState(): LiveData<State> = Transformations.switchMap(
        newsDataSourceFactory.newsDataSourceLiveData,
        NewsDataSource::state
    )

    fun retry() {
        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    fun setRetry(action: Action?) {
        newsDataSourceFactory.newsDataSourceLiveData.value?.setRetry(action)
    }

    fun updateState(state: State?) {
        newsDataSourceFactory.newsDataSourceLiveData.value?.updateState(state)
    }

    fun listIsEmpty(): Boolean {
        return newsLiveData.value?.isEmpty() ?: true
    }

    fun listIsInvalid(): Boolean {
        return newsLiveData.value?.dataSource?.isInvalid ?: false
    }
}