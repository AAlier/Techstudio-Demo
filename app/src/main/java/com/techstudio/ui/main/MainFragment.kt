package com.techstudio.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.techstudio.R
import com.techstudio.extensions.addFragment
import com.techstudio.ui.favourite.FavouriteFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {
    private val adapter: Adapter
        get() = viewPager.adapter as Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButton()

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.itemFavourite -> {
                    addFragment(FavouriteFragment.newInstance())
                }
            }
            return@setOnMenuItemClickListener true
        }
        viewPager.adapter = Adapter(this)

        bottomNavigation.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.itemEmailed -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.itemShared -> {
                    viewPager.currentItem = 1
                    true
                }
                R.id.itemViewed -> {
                    viewPager.currentItem = 2
                    true
                }
                else -> false
            }
        }
        viewPager.registerOnPageChangeCallback(onPageChangeListener)
    }

    private fun setupBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            })
    }

    private val onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            val titleRes = adapter.getPageTitle(position)
            toolbar.setTitle(titleRes)
            bottomNavigation.menu[position].isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.unregisterOnPageChangeCallback(onPageChangeListener)
    }
}