package com.balves42.paginglibrarydemo.mainActivity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.balves42.paginglibrarydemo.api.NewsModel
import com.balves42.paginglibrarydemo.mainActivity.adapters.NewsAdapter
import com.balves42.paginglibrarydemo.mainActivity.utils.State
import com.balves42.paginglibrarydemo.mainActivity.viewmodels.NewsViewModel
import com.balves42.paginglibrarydemo.mainActivity.viewmodels.NewsViewModelFactory
import com.balves42.paginglibrarydemo.R
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    MainActivityView {

    private lateinit var mPresenter: MainActivityPresenter
    private lateinit var mViewModel: NewsViewModel
    private lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter =
            MainActivityPresenterImpl(
                this,
                NewsModel()
            )

        mViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(
                mPresenter
            )
        ).get(
            NewsViewModel::class.java
        )

        mAdapter = NewsAdapter { mViewModel.retry()}
        recyclerView.adapter = mAdapter

        errorMessage.setOnClickListener { mViewModel.retry() }
        observeLiveData()
        buttonClickListener()
    }

    override fun invalidateList() {
        mViewModel.getNews().removeObservers(this)
        mViewModel.getState().removeObservers(this)
        mViewModel.newsLiveData.value?.dataSource?.invalidate()
        observeLiveData()
    }

    override fun updateState(state: State) {
        mViewModel.updateState(state)
    }

    override fun retry(){
        mViewModel.retry()
    }

    override fun setRetry(action: Action?) {
        mViewModel.setRetry(action)
    }

    override fun listIsInvalidated(): Boolean {
        return mViewModel.listIsInvalid()
    }

    override fun listIsEmpty(): Boolean {
        return mViewModel.listIsEmpty()
    }

    override fun setAdapterState(state: State) {
        mAdapter.setState(state)
    }

    override fun setListVisibility(show: Boolean) {
        recyclerView.visibility = show.toVisibilityGone()
    }

    override fun setErrorVisibility(show: Boolean) {
        errorMessage.visibility = show.toVisibilityGone()
    }

    override fun setPBVisibility(show: Boolean) {
        pbNews.visibility = show.toVisibilityGone()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.getNews().removeObservers(this)
        mViewModel.getState().removeObservers(this)
        mPresenter.cleanDisposables()
    }

    /**
     *  Observe live data emitted by view model
     *
     */
    private fun observeLiveData() {
        mViewModel.getNews().observe(this, Observer {
            mAdapter.submitList(it)
        })

        mViewModel.getState().observe(this, Observer {
            mPresenter.stateObserver(it)
        })
    }

    /**
     * Behaviour of the invalidate button
     *
     */
    private fun buttonClickListener() {
        btnInvalidate.setOnClickListener {
            invalidateList()
        }
    }

    /**
     * Converts a true / false in a View.VISIBLE / View.GONE
     *
     */
    private fun Boolean.toVisibilityGone() = if (this) View.VISIBLE else View.GONE
}
