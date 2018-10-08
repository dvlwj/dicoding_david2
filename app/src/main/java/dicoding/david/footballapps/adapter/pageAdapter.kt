package dicoding.david.footballapps.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import dicoding.david.footballapps.loadData.NextMatch
import dicoding.david.footballapps.loadData.LastMatch

class pageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val pages = listOf(
            LastMatch(),
            NextMatch()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Last Match"
            else -> "Next Match"
        }
    }
}