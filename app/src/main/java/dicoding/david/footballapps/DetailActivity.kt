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
import java.util.regex.Pattern

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
                val idHomeTeam = respondParser.string("idHomeTeam")
                val idAwayTeam = respondParser.string("idAwayTeam")
                val strHomeTeam = respondParser.string("strHomeTeam")
                val strAwayTeam = respondParser.string("strAwayTeam")
                val intHomeScore = respondParser.string("intHomeScore")
                val intAwayScore = respondParser.string("intAwayScore")
                val strHomeGoalDetails = respondParser.string("strHomeGoalDetails")
                val strHomeLineupGoalKeeper = respondParser.string("strHomeLineupGoalKeeper")
                val strHomeLineupDefense = respondParser.string("strHomeLineupDefense")
                val strHomeLineupMidfield = respondParser.string("strHomeLineupMidfield")
                val strHomeLineupForward = respondParser.string("strHomeLineupForward")
                val strHomeLineupSubstitutes = respondParser.string("strHomeLineupSubstitutes")
                val strAwayGoalDetails = respondParser.string("strAwayGoalDetails")
                val strAwayLineupGoalKeeper = respondParser.string("strAwayLineupGoalKeeper")
                val strAwayLineupDefense = respondParser.string("strAwayLineupDefense")
                val strAwayLineupMidfield = respondParser.string("strAwayLineupMidfield")
                val strAwayLineupForward = respondParser.string("strAwayLineupForward")
                val strAwayLineupSubstitutes = respondParser.string("strAwayLineupSubstitutes")
                val intHomeShot = respondParser.string("intHomeShot")
                val intAwayShot = respondParser.string("intAwayShot")
                val strDateEvent = respondParser.string("dateEvent")
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
                val strTeamLogo = respondParser.string("strTeamLogo")
                Picasso.get()?.load(strTeamLogo)?.into(homeTeamPicture)
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
                val strTeamLogo = respondParser.string("strTeamLogo")
                Picasso.get()?.load(strTeamLogo)?.into(awayTeamPicture)
            }
            result.failure {
                awayTeamLogo(idHomeTeam)
            }
        }
    }
}
