package ir.daneh.danehattendance

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import ir.daneh.danehattendance.adapter.PagerViewAdapter


class MainActivity : AppCompatActivity() {


    private lateinit var mViewPager: ViewPager
    private lateinit var homeBtn: ImageButton
    private lateinit var searchBtn: ImageButton
    private lateinit var addBtn: ImageButton
    private lateinit var notificationBtn: ImageButton
    private lateinit var personBtn: ImageButton
    private lateinit var mPagerAdapter: PagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewPager = findViewById(R.id.mViewPager)
        homeBtn = findViewById(R.id.homeBtn)
        searchBtn = findViewById(R.id.searchBtn)
        addBtn = findViewById(R.id.addBtn)
        notificationBtn = findViewById(R.id.notificationBtn)
        personBtn = findViewById(R.id.personBtn)
        mPagerAdapter = PagerViewAdapter(supportFragmentManager)
        mViewPager.adapter = mPagerAdapter
        mViewPager.offscreenPageLimit = 5


        homeBtn.setOnClickListener {
            mViewPager.currentItem = 0

        }

        searchBtn.setOnClickListener {

            mViewPager.currentItem = 1

        }

        addBtn.setOnClickListener {
            mViewPager.currentItem = 2

        }

        notificationBtn.setOnClickListener {
            mViewPager.currentItem = 3

        }

        personBtn.setOnClickListener {
            mViewPager.currentItem = 4

        }

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                changingTabs(position)
            }
        })

        // default tab

        mViewPager.currentItem = 0
        homeBtn.setImageResource(R.drawable.ic_home_pink)


    }








    private fun changingTabs(position: Int) {
        when (position) {
            0 -> {
                homeBtn.setImageResource(R.drawable.ic_home_pink)
                searchBtn.setImageResource(R.drawable.ic_search_black)
                addBtn.setImageResource(R.drawable.ic_add_black)
                notificationBtn.setImageResource(R.drawable.ic_notifications_none_black)
                personBtn.setImageResource(R.drawable.ic_person_outline_black)
            }
            1 -> {
                homeBtn.setImageResource(R.drawable.ic_home_black)
                searchBtn.setImageResource(R.drawable.ic_search_pink)
                addBtn.setImageResource(R.drawable.ic_add_black)
                notificationBtn.setImageResource(R.drawable.ic_notifications_none_black)
                personBtn.setImageResource(R.drawable.ic_person_outline_black)
            }
            2 -> {
                homeBtn.setImageResource(R.drawable.ic_home_black)
                searchBtn.setImageResource(R.drawable.ic_search_black)
                addBtn.setImageResource(R.drawable.ic_add_pink)
                notificationBtn.setImageResource(R.drawable.ic_notifications_none_black)
                personBtn.setImageResource(R.drawable.ic_person_outline_black)
            }
            3 -> {
                homeBtn.setImageResource(R.drawable.ic_home_black)
                searchBtn.setImageResource(R.drawable.ic_search_black)
                addBtn.setImageResource(R.drawable.ic_add_black)
                notificationBtn.setImageResource(R.drawable.ic_notifications_none_pink)
                personBtn.setImageResource(R.drawable.ic_person_outline_black)
            }
            4 -> {
                homeBtn.setImageResource(R.drawable.ic_home_black)
                searchBtn.setImageResource(R.drawable.ic_search_black)
                addBtn.setImageResource(R.drawable.ic_add_black)
                notificationBtn.setImageResource(R.drawable.ic_notifications_none_black)
                personBtn.setImageResource(R.drawable.ic_person_outline_pink)
            }
        }

    }
}
