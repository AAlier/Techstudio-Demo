package com.techstudio.ui.favourite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.techstudio.R
import com.techstudio.extensions.addFragment
import com.techstudio.extensions.observeNonNull
import com.techstudio.ui.detail.ArticleFragment
import com.techstudio.ui.main.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_favourite.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    companion object {
        fun newInstance() = FavouriteFragment()
    }

    private val viewModel: FavouriteViewModel by viewModel()

    private val adapter by lazy {
        ArticleAdapter(
            onItemClickListener = { article ->
                addFragment(ArticleFragment.newInstance(article.id))
            },
            onArticleCheck = {

            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.adapter = adapter
        viewModel.items.observeNonNull(viewLifecycleOwner) {
            adapter.setData(it)
            emptyView.isVisible = it.isEmpty()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getArticles()
    }
}