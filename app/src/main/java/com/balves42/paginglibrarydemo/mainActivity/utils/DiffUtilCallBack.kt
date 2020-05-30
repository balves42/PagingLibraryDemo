package com.balves42.paginglibrarydemo.mainActivity.utils

import androidx.recyclerview.widget.DiffUtil
import com.balves42.paginglibrarydemo.api.News

class DiffUtilCallBack : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }

}