package dicoding.david.footballapps

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import com.squareup.picasso.Picasso
import dicoding.david.footballapps.R.drawable.ic_favorite
import dicoding.david.footballapps.R.drawable.ic_favorite_border
import dicoding.david.footballapps.R.id.add_to_favorite
import dicoding.david.footballapps.R.menu.detail_menu
import dicoding.david.footballapps.databaseHelper.Favorite
import dicoding.david.footballapps.databaseHelper.database
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail_activity.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity() {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var idEventData: String? = null
    private var dateEventData: String? = null
    private var homeTeamData: String? = null
    private var homeScoreData: String? = null
    private var awayTeamData: String? = null
    private var awayScoreData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        idEventData = intent.getStringExtra("idEvent")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        favoriteState(idEventData.toString())
        loadData(idEventData)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite(idEventData.toString()) else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.EVENT_ID to idEventData,
                        Favorite.HOME_TEAM to homeTeamData,
                        Favorite.HOME_SCORE to homeScoreData,
                        Favorite.AWAY_TEAM to awayTeamData,
                        Favorite.AWAY_SCORE to awayScoreData,
                        Favorite.DATE_EVENT to dateEventData)
            }
            Snackbar.make(detailActivity,"Added to Favorites", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(detailActivity,"Added to Favorites", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite(idEvent: String){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                        "id" to idEvent)
            }
            Snackbar.make(detailActivity,"Remove from Favorites", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(detailActivity,"Remove from Favorites", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite)
        }
        else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite_border)
        }
    }

    private fun favoriteState(idEvent: String){
        database.use {
            //            val idEvent = intent.getStringExtra("idEventData")
            val result = select(Favorite.TABLE_FAVORITE).whereArgs("(EVENT_ID = {id})", "id" to idEvent)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
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
                    val strHomeGoalDetails = row?.getString("strHomeGoalDetails")?.replace(';','\n')
                    val strHomeLineupGoalKeeper = row?.getString("strHomeLineupGoalkeeper")?.replace(';',' ')
                    val strHomeLineupDefense = row?.getString("strHomeLineupDefense")?.replace(';','\n')
                    val strHomeLineupMidfield = row?.getString("strHomeLineupMidfield")?.replace(';','\n')
                    val strHomeLineupForward = row?.getString("strHomeLineupForward")?.replace(';','\n')
                    val strHomeLineupSubstitutes = row?.getString("strHomeLineupSubstitutes")?.replace(';','\n')
                    val strAwayGoalDetails = row?.getString("strAwayGoalDetails")?.replace(';','\n')
                    val strAwayLineupGoalKeeper = row?.getString("strAwayLineupGoalkeeper")?.replace(';',' ')
                    val strAwayLineupDefense = row?.getString("strAwayLineupDefense")?.replace(';','\n')
                    val strAwayLineupMidfield = row?.getString("strAwayLineupMidfield")?.replace(';','\n')
                    val strAwayLineupForward = row?.getString("strAwayLineupForward")?.replace(';','\n')
                    val strAwayLineupSubstitutes = row?.getString("strAwayLineupSubstitutes")?.replace(';','\n')
                    val intHomeShot = row?.getString("intHomeShots")
                    val intAwayShot = row?.getString("intAwayShots")
                    val strDateEvent = row?.getString("dateEvent")
                    val indonesiaDateFormat = Locale("in","ID","ID")
                    val dateTime = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(strDateEvent)
                    val dateTime2 = SimpleDateFormat("EEEE, dd MMMM yyyy", indonesiaDateFormat).format(dateTime)
                    homeTeamData = strHomeTeam
                    awayTeamData = strAwayTeam
                    awayScoreData = intAwayScore
                    homeScoreData = intHomeScore
                    dateEventData = strDateEvent
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
