package dicoding.david.footballapps.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import dicoding.david.footballapps.loadData.FavoriteMatch
import dicoding.david.footballapps.loadData.LastMatch
import dicoding.david.footballapps.loadData.NextMatch

class pageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val pages = listOf(
            LastMatch(),
            NextMatch(),
            FavoriteMatch()
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
            1 -> "Next Match"
            else -> "Favorite Match"
        }
    }
}