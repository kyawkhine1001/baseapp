package com.kkk.mylibrary.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

class ViewPagerAdapter(fm: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fm) {
    private val fragments = ArrayList<Fragment>()
    private val fragmentsTitle = ArrayList<String>()

    fun addFragments(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentsTitle.add(title)
        notifyDataSetChanged()
    }

    fun removeFragmentAtPosition(position: Int) {
        if (fragments.size > position) {
            fragments.removeAt(position)
        }
        if (fragmentsTitle.size > position) {
            fragmentsTitle.removeAt(position)
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentsTitle[position]
    }

    fun setPageTitle(position: Int, title: String) {
        fragmentsTitle[position] = title
        notifyDataSetChanged()
    }
}