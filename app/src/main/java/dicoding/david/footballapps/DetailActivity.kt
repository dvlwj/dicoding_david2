package dicoding.david.footballapps

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.beust.klaxon.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail_activity.*
import java.text.SimpleDateFormat
import java.util.*
import org.json.JSONArray



class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val idEvent = intent.getStringExtra("idEvent")
        loadData(idEvent)
    }

    private fun loadData(idEvent: String?){
        Fuel.get(serverList.matchDetail+idEvent).responseJson{ _, response, result ->
            result.success {
                val respond = String(response.data)
                val stringBuilder = StringBuilder(respond)
                val respondParser = Parser().parse(stringBuilder) as JsonObject
                val data = respondParser.array<String>("events")
                val array = JSONArray(data)
                for (i in 0 until array.length()) {
                    val row = array.getJSONObject(i)
                    val idHomeTeam = row?.getString("idHomeTeam")
                    val idAwayTeam = row?.getString("idAwayTeam")
                    val strHomeTeam = row?.getString("strHomeTeam")
                    val strAwayTeam = row?.getString("strAwayTeam")
                    val intHomeScore = row?.getString("intHomeScore")
                    val intAwayScore = row?.getString("intAwayScore")
                    val strHomeGoalDetails = row?.getString("strHomeGoalDetails")
                    val strHomeLineupGoalKeeper = row?.getString("strHomeLineupGoalkeeper")
                    val strHomeLineupDefense = row?.getString("strHomeLineupDefense")
                    val strHomeLineupMidfield = row?.getString("strHomeLineupMidfield")
                    val strHomeLineupForward = row?.getString("strHomeLineupForward")
                    val strHomeLineupSubstitutes = row?.getString("strHomeLineupSubstitutes")
                    val strAwayGoalDetails = row?.getString("strAwayGoalDetails")
                    val strAwayLineupGoalKeeper = row?.getString("strAwayLineupGoalkeeper")
                    val strAwayLineupDefense = row?.getString("strAwayLineupDefense")
                    val strAwayLineupMidfield = row?.getString("strAwayLineupMidfield")
                    val strAwayLineupForward = row?.getString("strAwayLineupForward")
                    val strAwayLineupSubstitutes = row?.getString("strAwayLineupSubstitutes")
                    val intHomeShot = row?.getString("intHomeShots")
                    val intAwayShot = row?.getString("intAwayShots")
                    val strDateEvent = row?.getString("dateEvent")
                    val indonesiaDateFormat = Locale("in","ID","ID")
                    val dateTime = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(strDateEvent)
                    val dateTime2 = SimpleDateFormat("EEEE, dd MMMM yyyy", indonesiaDateFormat).format(dateTime)
                    dateEvent.text = dateTime2
                    homeTeam.text = strHomeTeam
                    awayTeam.text = strAwayTeam
                    homeScore.text = intHomeScore.toString()
                    awayScore.text = intAwayScore.toString()
                    goalHome.text = strHomeGoalDetails
                    goalAway.text = strAwayGoalDetails
                    gkHome.text = strHomeLineupGoalKeeper
                    gkAway.text = strAwayLineupGoalKeeper
                    dfHome.text = strHomeLineupDefense
                    dfAway.text = strAwayLineupDefense
                    mfHome.text = strHomeLineupMidfield
                    mfAway.text = strAwayLineupMidfield
                    fwHome.text = strHomeLineupForward
                    fwAway.text = strAwayLineupForward
                    subsHome.text = strHomeLineupSubstitutes
                    subsAway.text = strAwayLineupSubstitutes
                    shotsHome.text = intHomeShot.toString()
                    shotsAway.text = intAwayShot.toString()
                    homeTeamLogo(idHomeTeam)
                    awayTeamLogo(idAwayTeam)
                }
            }
            result.failure {
                loadData(idEvent)
            }
        }
    }

    private fun homeTeamLogo(idHomeTeam: String?){
        Fuel.get(serverList.teamDetail+idHomeTeam).responseJson{_, response, result ->
            result.success {
                val respond = String(response.data)
                val stringBuilder = StringBuilder(respond)
                val respondParser = Parser().parse(stringBuilder) as JsonObject
                val data = respondParser.array<String>("teams")
                val array = JSONArray(data)
                for (i in 0 until array.length()) {
                    val row = array.getJSONObject(i)
                    val strTeamLogo = row?.getString("strTeamBadge")
                    Picasso.get()?.load(strTeamLogo)?.into(homeTeamPicture)
                }
            }
            result.failure {
                homeTeamLogo(idHomeTeam)
            }
        }
    }

    private fun awayTeamLogo(idHomeTeam: String?){
        Fuel.get(serverList.teamDetail+idHomeTeam).responseJson{_, response, result ->
            result.success {
                val respond = String(response.data)
                val stringBuilder = StringBuilder(respond)
                val respondParser = Parser().parse(stringBuilder) as JsonObject
                val data = respondParser.array<String>("teams")
                val array = JSONArray(data)
                for (i in 0 until array.length()) {
                    val row = array.getJSONObject(i)
                    val strTeamLogo = row?.getString("strTeamBadge")
                    Picasso.get()?.load(strTeamLogo)?.into(awayTeamPicture)
                }
            }
            result.failure {
                awayTeamLogo(idHomeTeam)
            }
        }
    }
}
