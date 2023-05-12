package com.example.myproject.user_info.user_history.user_history_fragments
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val COUNT = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> UserActivitiesHistoryFragment()
            1 -> UserParticipationHistoryFragment()
            else -> UserActivitiesHistoryFragment()
        }
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): String =
        when (position) {
            0 -> "activities history"
            1-> "participation history"
            else -> "activities history"
        }

}


