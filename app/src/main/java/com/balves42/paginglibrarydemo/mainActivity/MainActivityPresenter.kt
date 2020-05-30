package com.balves42.paginglibrarydemo.mainActivity

import androidx.paging.PageKeyedDataSource.*
import com.balves42.paginglibrarydemo.api.News
import com.balves42.paginglibrarydemo.mainActivity.utils.State
import io.reactivex.disposables.CompositeDisposable

interface MainActivityPresenter {

    /**
     * Clean the disposables of the requests
     *
     */
    fun cleanDisposables()

    /**
     * Observer of the states with the behaviours
     *
     * @param state state of the list
     */
    fun stateObserver(state: State?)

    /**
     * Behaviour of the initial request
     *
     * @param params params with page size
     * @param callback callback object
     */
    fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>)

    /**
     * Behaviour of the following requests
     *
     * @param params params with page load and size
     * @param callback callback object
     */
    fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, News>
    )

    /**
     * Get the disposable used with the requests
     *
     * @return disposable
     */
    fun getDisposable(): CompositeDisposable


}