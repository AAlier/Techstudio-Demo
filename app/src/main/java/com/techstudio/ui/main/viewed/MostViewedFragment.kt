package com.techstudio.ui.main.viewed

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.techstudio.R
import com.techstudio.extensions.addFragment
import com.techstudio.extensions.observeNonNull
import com.techstudio.extensions.parse
import com.techstudio.ui.detail.ArticleFragment
import com.techstudio.ui.main.ArticleAdapter
import com.techstudio.util.BookMarkSwipeCallback
import kotlinx.android.synthetic.main.fragment_most_viewed.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MostViewedFragment : Fragment(R.layout.fragment_most_viewed) {

    companion object {
        fun newInstance() = MostViewedFragment()
    }

    private val viewModel: MostViewedViewModel by viewModel()

    private val adapter by lazy {
        ArticleAdapter(
            onItemClickListener = { article ->
                addFragment(ArticleFragment.newInstance(article.id))
            },
            onArticleCheck = {
                viewModel.toggle(it.id)
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
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        viewModel.items.observeNonNull(viewLifecycleOwner, adapter::setData)
        viewModel.isRefreshing.observeNonNull(viewLifecycleOwner) { isLoading ->
            swipeRefreshLayout.isRefreshing = isLoading
            errorView.isVisible = false
        }
        viewModel.error.observeNonNull(viewLifecycleOwner) {
            errorView.setMessage(it.parse(requireContext()))
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getArticles()
        }
        errorView.onRefreshListener = {
            viewModel.getArticles()
        }
    }


    private val swipeHandler = object : BookMarkSwipeCallback() {
        override fun onRightSwiped(viewHolder: RecyclerView.ViewHolder) {

        }

        override fun onLeftSwiped(viewHolder: RecyclerView.ViewHolder) {
            val article = adapter.getItem(viewHolder.adapterPosition)
            viewModel.toggle(article.id)
        }
    }
}