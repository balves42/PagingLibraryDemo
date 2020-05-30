package com.balves42.paginglibrarydemo.mainActivity

import androidx.paging.PageKeyedDataSource.*
import com.balves42.paginglibrarydemo.api.News
import com.balves42.paginglibrarydemo.api.NewsModel
import com.balves42.paginglibrarydemo.mainActivity.utils.State
import com.balves42.paginglibrarydemo.mainActivity.utils.State.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action

class MainActivityPresenterImpl(
    private val mView: MainActivityView,
    private val mNewsModel: NewsModel
) : MainActivityPresenter {

    private val mDisposable = CompositeDisposable()

    override fun cleanDisposables() {
        mDisposable.clear()
    }

    override fun stateObserver(state: State?) {
        val listInvalidated = mView.listIsInvalidated()
        val listEmpty = mView.listIsEmpty()

        when (state) {
            LOADING -> loadingStateBehaviour(listEmpty, listInvalidated)
            ERROR -> errorStateBehaviour(listEmpty, listInvalidated)
            else -> otherStateBehaviour()
        }

        if (!mView.listIsEmpty()) {
            mView.setAdapterState(state ?: DONE)
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        mView.updateState(LOADING)
        mDisposable.add(
            mNewsModel.getNews(1, params.requestedLoadSize)
                .subscribe({ response ->
                    mView.updateState(DONE)
                    callback.onResult(
                        response.news,
                        null,
                        2
                    )
                }, {
                    mView.updateState(ERROR)
                    mView.setRetry(Action { loadInitial(params, callback) })
                })
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, News>
    ) {
        mView.updateState(LOADING)
        mDisposable.add(
            mNewsModel.getNews(params.key, params.requestedLoadSize)
                .subscribe({ response ->
                    mView.updateState(DONE)
                    callback.onResult(
                        response.news,
                        params.key + 1
                    )
                }, {
                    mView.updateState(ERROR)
                    mView.setRetry(Action { loadAfter(params, callback) })
                }
                )
        )
    }

    override fun getDisposable(): CompositeDisposable {
        return mDisposable
    }

    /**
     * Behaviour when the state is different than LOADING or DONE
     *
     */
    private fun otherStateBehaviour() {
        mView.setListVisibility(true)
    }

    /**
     * Behaviour when the state is ERROR
     *
     * @param listEmpty true / false accordingly
     * @param listInvalidated true / false accordingly
     */
    private fun errorStateBehaviour(listEmpty: Boolean, listInvalidated: Boolean) {
        mView.setListVisibility(true)

        if (listEmpty || listInvalidated) {
            mView.setErrorVisibility(true)
            mView.setPBVisibility(false)
        } else {
            mView.setErrorVisibility(false)
        }
    }

    /**
     * Behaviour when the state is ERROR
     *
     * @param listInvalidated true / false accordingly
     * @param listEmpty true / false accordingly
     */
    private fun loadingStateBehaviour(listEmpty: Boolean, listInvalidated: Boolean) {
        mView.setErrorVisibility(false)
        mView.setListVisibility(!listInvalidated)
        mView.setPBVisibility(listEmpty || listInvalidated)
    }
}