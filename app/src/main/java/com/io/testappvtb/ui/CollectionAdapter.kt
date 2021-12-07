package com.io.testappvtb.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.io.testappvtb.data.models.VideoResultModel
import com.io.testappvtb.ui.fragments.FirstFragment

class CollectionAdapter(
    fragmentActivity: FragmentActivity,
    private val response: VideoResultModel
) :
    FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> FirstFragment(response.single.toString())
            1 -> FirstFragment(response.split_v.toString())
            2 -> FirstFragment(response.split_h.toString())
            3 -> FirstFragment(response.src.toString())
            else -> FirstFragment(response.single.toString())
        }
    }
}