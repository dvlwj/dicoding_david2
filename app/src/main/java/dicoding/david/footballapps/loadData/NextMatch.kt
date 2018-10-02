package dicoding.david.footballapps.loadData

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import dicoding.david.footballapps.R.layout.fragment_main
import dicoding.david.footballapps.adapter.NextMatchAdapter
import dicoding.david.footballapps.serverList
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONObject
import java.util.ArrayList

class NextMatch : Fragment(), NextMatchAdapter.MyListerner {

    private var ArrayList: ArrayList<NextMatchModel>? = null
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

    fun loadData(){
        Fuel.get(serverList.next15).responseJson{request, response, result ->
            result.success {
                val respond = String(response.data)
                val stringBuilder = StringBuilder(respond)
                val respondParser = Parser().parse(stringBuilder) as JsonObject
                val data = respondParser.array<JsonObject>("Data")
                val arrayList = data?.map { it ->
                    NextMatchModel(
                            it.string("strHomeTeam"),
                            it.string("strAwayTeam"),
                            it.string("intHomeScore"),
                            it.string("intAwayScore"),
                            it.string("dateEvent")
                    )
                }
                arrayList = ArrayList(arrayList)
                val recycleView = match_list
                val adapter = N

            }
            result.failure {
                loadData()
            }
        }
    }
}