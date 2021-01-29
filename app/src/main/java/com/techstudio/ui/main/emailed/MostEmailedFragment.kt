package com.techstudio.ui.main.emailed

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.techstudio.R
import com.techstudio.extensions.addFragment
import com.techstudio.extensions.observeNonNull
import com.techstudio.extensions.parse
import com.techstudio.ui.detail.ArticleFragment
import com.techstudio.ui.main.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_most_emailed.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MostEmailedFragment : Fragment(R.layout.fragment_most_emailed) {

    companion object {
        fun newInstance() = MostEmailedFragment()
    }

    private val viewModel: MostEmailedViewModel by viewModel()
    private val adapter by lazy {
        ArticleAdapter(
            onItemClickListener = { article ->
                addFragment(ArticleFragment.newInstance(article.id))
            },
            onArticleCheck = {
                viewModel.onAddFavourite(it)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.items.observeNonNull(viewLifecycleOwner, adapter::setData)
        viewModel.error.observeNonNull(viewLifecycleOwner) {
            errorView.setMessage(it.parse(requireContext()))
        }
        viewModel.isRefreshing.observeNonNull(viewLifecycleOwner) { isLoading ->
            swipeRefreshLayout.isRefreshing = isLoading
            errorView.isVisible = false
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getArticles()
        }
        errorView.onRefreshListener = {
            viewModel.getArticles()
        }
    }
}