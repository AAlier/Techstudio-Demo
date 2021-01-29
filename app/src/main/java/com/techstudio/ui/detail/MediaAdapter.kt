package com.techstudio.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.techstudio.R
import com.techstudio.model.Media
import com.techstudio.model.MediaMetadata
import com.techstudio.ui.base.BaseAdapter
import com.techstudio.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_article_media.view.*
import kotlinx.android.synthetic.main.item_media_metadata.view.*

class MediaAdapter : BaseAdapter<Media>() {
    init {
        addViewHolderCreator(Media::class.java) {
            ViewHolder(it)
        }
    }
}

private class ViewHolder(
    parent: ViewGroup
) : BaseViewHolder<Media>(R.layout.item_article_media, parent) {
    private val captionTextView = itemView.captionTextView
    private val recyclerView = itemView.mediaMetaDataRecyclerView
    private val adapter by lazy { MediaMetaDataAdapter() }

    init {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(
            3, StaggeredGridLayoutManager.VERTICAL
        )
    }

    override fun bind(item: Media) {
        captionTextView.text = item.copyright
        adapter.setData(item.mediaMetadata)
    }
}

class MediaMetaDataAdapter : BaseAdapter<MediaMetadata>() {
    init {
        addViewHolderCreator(MediaMetadata::class.java) {
            MediaViewHolder(it)
        }
    }
}

private class MediaViewHolder(
    parent: ViewGroup
) : BaseViewHolder<MediaMetadata>(R.layout.item_media_metadata, parent) {
    private val imageView = itemView.mediaImageView

    override fun bind(item: MediaMetadata) {
        imageView.loadImage(item.url)
    }
}