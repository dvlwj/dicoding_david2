package dicoding.david.footballapps.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dicoding.david.footballapps.R.layout.fragment_fav_match
import dicoding.david.footballapps.adapter.FavoriteMatchAdapter
import dicoding.david.footballapps.database.databaseHelper
import dicoding.david.footballapps.database.databaseHelper.database
import dicoding.david.footballapps.model.FavoriteMatchModel
import kotlinx.android.synthetic.main.fragment_fav_match.*
import kotlinx.android.synthetic.main.fragment_fav_match.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select


class FavoriteMatch() : Fragment(), FavoriteMatchAdapter.MyListener {

    private val favoriteMatchArrayList = ArrayList<FavoriteMatchModel>()
    private var ctx: Context? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(fragment_fav_match, container, false)
        rootView.swipe_container_fav_match.setOnRefreshListener {
            rootView.swipe_container_fav_match.isRefreshing = false
            favoriteMatchArrayList.clear()
            showLoading()
            loadData()
            rootView.swipe_container_fav_match.isRefreshing = false
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteMatchArrayList.clear()
        loadData()
    }

    fun loadData(){
        context?.database?.use {
            val result = select(databaseHelper.Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatchModel>())
            favoriteMatchArrayList.addAll(favorite)
            val recyclerView = match_list_fav_match
            val adapter = FavoriteMatchAdapter(favoriteMatchArrayList,this@FavoriteMatch)
            val layoutManager = LinearLayoutManager(context)
            recyclerView?.layoutManager = layoutManager
            recyclerView?.adapter = adapter
            hideLoading()
        }
    }

    private fun showLoading(){
        this.progressBar.visibility = View.VISIBLE
    }
    private fun hideLoading(){
        this.progressBar.visibility = View.GONE
    }

    override fun onHolderClick(idEvent: String?) {
        ctx?.let {
            startActivity(Intent(it, DetailActivity::class.java).putExtra("idEvent",idEvent))
        }
    }
}