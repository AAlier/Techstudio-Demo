package com.techstudio.ui.main

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.techstudio.R
import com.techstudio.ui.main.emailed.MostEmailedFragment
import com.techstudio.ui.main.shared.MostSharedFragment
import com.techstudio.ui.main.viewed.MostViewedFragment

class Adapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val items = listOf(
        FragmentItem(
            { MostEmailedFragment.newInstance() },
            R.string.most_emailed
        ),
        FragmentItem(
            { MostSharedFragment.newInstance() },
            R.string.most_shared
        ),
        FragmentItem(
            { MostViewedFragment.newInstance() },
            R.string.most_viewed
        )
    )

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return items[position].fragment
    }

    fun getPageTitle(position: Int) = items[position].titleRes
}

data class FragmentItem(
    val fragmentCreator: () -> Fragment,
    @StringRes val titleRes: Int
) {
    val fragment: Fragment
        get() = fragmentCreator()
}
