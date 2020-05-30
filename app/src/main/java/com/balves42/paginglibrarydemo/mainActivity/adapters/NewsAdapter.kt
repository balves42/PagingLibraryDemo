package com.balves42.paginglibrarydemo.mainActivity.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.balves42.paginglibrarydemo.api.News
import com.balves42.paginglibrarydemo.mainActivity.utils.DiffUtilCallBack
import com.balves42.paginglibrarydemo.mainActivity.utils.State
import com.balves42.paginglibrarydemo.mainActivity.utils.State.ERROR
import com.balves42.paginglibrarydemo.mainActivity.utils.State.LOADING
import com.balves42.paginglibrarydemo.R
import kotlinx.android.synthetic.main.news_item_loading.view.*
import kotlinx.android.synthetic.main.news_item_section.view.*

class NewsAdapter(
    private val retry: () -> Unit
) : PagedListAdapter<News, RecyclerView.ViewHolder>(DiffUtilCallBack()) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item_section, parent, false)
            return NewsViewHolder(
                view
            )
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item_loading, parent, false)
            FooterViewHolder(retry, view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as NewsViewHolder).bind(getItem(position))
        else (holder as FooterViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == LOADING || state == ERROR)
    }

    class NewsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(news: News?) {
            if (news != null) {
                itemView.txt_news_name.text = news.title
            }
        }
    }

    class FooterViewHolder(
        private val retry: () -> Unit,
        view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind(status: State?) {
            itemView.pbNews.visibility =
                if (status == LOADING) View.VISIBLE else View.INVISIBLE
            itemView.errorMessage.visibility =
                if (status == ERROR) View.VISIBLE else View.INVISIBLE
            itemView.errorMessage.setOnClickListener { retry() }
        }
    }
}