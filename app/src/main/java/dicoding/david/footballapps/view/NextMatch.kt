package dicoding.david.footballapps.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import dicoding.david.footballapps.R.layout.fragment_next_match
import dicoding.david.footballapps.adapter.NextMatchAdapter
import dicoding.david.footballapps.model.NextMatchModel
import dicoding.david.footballapps.api.serverList
import kotlinx.android.synthetic.main.fragment_next_match.*
import kotlinx.android.synthetic.main.fragment_next_match.view.*
import java.util.*

class NextMatch : Fragment(), NextMatchAdapter.MyListener {

    private var nextMatchArrayList: ArrayList<NextMatchModel>? = null
    private var ctx: Context? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(fragment_next_match, container, false)
        rootView.swipe_container.setOnRefreshListener {
            rootView.swipe_container.isRefreshing = false
            nextMatchArrayList?.clear()
            showLoading()
            loadData()
            rootView.swipe_container.isRefreshing = false
        }
        return rootView    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    fun loadData(){
        Fuel.get(serverList.next15).responseJson{ _, response, result ->
            result.success {
                val respond = String(response.data)
                val stringBuilder = StringBuilder(respond)
                val respondParser = Parser().parse(stringBuilder) as JsonObject
                val data = respondParser.array<JsonObject>("events")
                val arrayList = data?.map(fun(it: JsonObject): NextMatchModel {
                    return NextMatchModel(
                            it.string("idEvent"),
                            it.string("strHomeTeam"),
                            it.string("strAwayTeam"),
                            it.string("intHomeScore"),
                            it.string("intAwayScore"),
                            it.string("dateEvent")
                    )
                })
                this.nextMatchArrayList = ArrayList(arrayList)
                val recyclerView = match_list_next_match
                val adapter = NextMatchAdapter(nextMatchArrayList,this@NextMatch)
                val layoutManager = LinearLayoutManager(context)
                recyclerView?.layoutManager = layoutManager
                recyclerView?.adapter = adapter
                hideLoading()
            }
            result.failure {
                loadData()
            }
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