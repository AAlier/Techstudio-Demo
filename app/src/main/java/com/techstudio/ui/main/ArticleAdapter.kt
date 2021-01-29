package com.techstudio.ui.main

import android.view.ViewGroup
import com.techstudio.R
import com.techstudio.model.Article
import com.techstudio.ui.base.BaseAdapter
import com.techstudio.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleAdapter(
    onItemClickListener: (Article) -> Unit,
    onArticleCheck: (Article) -> Unit
) : BaseAdapter<Article>() {
    init {
        addViewHolderCreator(Article::class.java) {
            ViewHolder(it, onItemClickListener, onArticleCheck)
        }
    }
}

private class ViewHolder(
    parent: ViewGroup,
    onItemClickListener: (Article) -> Unit,
    private val onArticleCheck: (Article) -> Unit
) : BaseViewHolder<Article>(R.layout.item_article, parent, onItemClickListener) {
    private val imageView = itemView.imageView
    private val textView = itemView.topTextView
    private val infoTextView = itemView.bottomTextView

    override fun bind(item: Article) {
        imageView.loadImage(item.thumbnail)
        textView.text = item.title
        infoTextView.text = item.info
    }

    override fun onViewRecycled() {
        super.onViewRecycled()
        imageView.clear()
    }
}