package com.balves42.paginglibrarydemo.mainActivity.repositories

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.balves42.paginglibrarydemo.api.News
import com.balves42.paginglibrarydemo.mainActivity.MainActivityPresenter
import com.balves42.paginglibrarydemo.mainActivity.utils.State
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class NewsDataSource(
    private val mPresenter: MainActivityPresenter
) :
    PageKeyedDataSource<Int, News>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        mPresenter.loadInitial(params, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        mPresenter.loadAfter(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {}

    override fun invalidate() {
        mPresenter.cleanDisposables()
        super.invalidate()
    }

    fun retry() {
        if (retryCompletable != null) {
            mPresenter.getDisposable().add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    fun updateState(state: State?) {
        this.state.postValue(state)
    }

    fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}

