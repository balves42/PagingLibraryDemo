package com.balves42.paginglibrarydemo.mainActivity.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.balves42.paginglibrarydemo.mainActivity.MainActivityPresenter

class NewsViewModelFactory(
    private val mPresenter: MainActivityPresenter
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.cast(
            NewsViewModel(
                mPresenter
            )
        )!!
    }
}