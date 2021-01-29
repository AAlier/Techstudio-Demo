package com.techstudio.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.techstudio.R
import com.techstudio.extensions.args
import com.techstudio.extensions.observeNonNull
import com.techstudio.extensions.withArgs
import kotlinx.android.synthetic.main.fragment_article_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val EXTRA_ID = "EXTRA_ID"

class ArticleFragment : Fragment(R.layout.fragment_article_detail) {

    companion object {
        fun newInstance(id: Long) = ArticleFragment().withArgs(
            EXTRA_ID to id
        )
    }

    private val id: Long by args(EXTRA_ID)
    private val viewModel: ArticleViewModel by viewModel {
        parametersOf(id)
    }
    private val adapter by lazy {
        MediaAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.itemFavourite -> {
                    viewModel.toggleFavourite()
                    true
                }
                else -> false
            }
        }

        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        mediaRecyclerView.adapter = adapter
        viewModel.article.observeNonNull(viewLifecycleOwner) {
            imageView.loadImage(it.thumbnail)
            imageView.isVisible = it.thumbnail.isNotEmpty()

            topTextView.text = it.title
            topTextView.isVisible = it.title.isNotEmpty()

            bottomTextView.text = it.info
            bottomTextView.isVisible = it.info.isNotEmpty()

            adapter.setData(it.media)

            val icon =
                if (it.isFavourite) R.drawable.ic_bookmark_checked else R.drawable.ic_bookmark_unchecked
            toolbar.menu.findItem(R.id.itemFavourite).setIcon(icon)
        }
    }
}