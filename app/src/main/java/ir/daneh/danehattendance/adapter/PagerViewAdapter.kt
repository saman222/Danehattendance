package ir.daneh.danehattendance.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ir.daneh.danehattendance.fragments.*

class PagerViewAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {

        return when(position){

            0 ->{
                HomeFragment()
            }
            1 ->{
                SearchFragment()
            }
            2 ->{
                AddFragment()
            }
            3 ->{
                NotificationFragment()
            }
            4 ->{
                PersonFragment()
            }
            else -> {
                HomeFragment()
            }
        }
    }

    override fun getCount(): Int {

        return 5
    }
}