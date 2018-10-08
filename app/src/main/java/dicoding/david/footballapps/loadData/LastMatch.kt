package dicoding.david.footballapps.loadData

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
import dicoding.david.footballapps.DetailActivity
import dicoding.david.footballapps.R.layout.fragment_main
import dicoding.david.footballapps.adapter.LastMatchAdapter
import dicoding.david.footballapps.model.LastMatchModel
import dicoding.david.footballapps.serverList
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

class LastMatch : Fragment(), LastMatchAdapter.MyListener {

    private var lastMatchArrayList: ArrayList<LastMatchModel>? = null
    private var ctx: Context? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData(){
        Fuel.get(serverList.last15).responseJson{ _, response, result ->
            result.success {
                val respond = String(response.data)
                val stringBuilder = StringBuilder(respond)
                val respondParser = Parser().parse(stringBuilder) as JsonObject
                val data = respondParser.array<JsonObject>("events")
                val arrayList = data?.map(fun(it: JsonObject): LastMatchModel {
                    return LastMatchModel(
                            it.string("idEvent"),
                            it.string("strHomeTeam"),
                            it.string("strAwayTeam"),
                            it.string("intHomeScore"),
                            it.string("intAwayScore"),
                            it.string("dateEvent")
                    )
                })
                this.lastMatchArrayList = ArrayList(arrayList)
                val recyclerView = match_list
                val adapter = LastMatchAdapter(lastMatchArrayList,this@LastMatch)
                val layoutManager = LinearLayoutManager(context)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
            }
            result.failure {
                loadData()
            }
        }
    }

    override fun onHolderClick(idEvent: String?) {
        ctx?.let {
            startActivity(Intent(it, DetailActivity::class.java).putExtra("idEvent",idEvent))
        }
    }
}