package dicoding.david.footballapps.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dicoding.david.footballapps.R
import dicoding.david.footballapps.adapter.pageAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager.adapter = pageAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewPager)
    }
}
