package dicoding.david.footballapps.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*
import dicoding.david.footballapps.R.layout.match_list
import dicoding.david.footballapps.model.FavoriteMatchModel
import kotlinx.android.synthetic.main.match_list.view.*
import java.text.SimpleDateFormat


class FavoriteMatchAdapter(private val dataList: ArrayList<FavoriteMatchModel>?, private val listener: MyListener) : RecyclerView.Adapter<FavoriteMatchAdapter.FavoriteMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMatchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(match_list, parent, false)
        return FavoriteMatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteMatchViewHolder, position: Int) {
        dataList?.let {
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    inner class FavoriteMatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventBody: ConstraintLayout = itemView.eventBody
        val strHomeTeam: TextView = itemView.teamHome
        val strAwayTeam: TextView = itemView.teamAway
        val intHomeTeam: TextView = itemView.scoreHome
        val intAwayTeam: TextView = itemView.scoreAway
        val dateEvent: TextView = itemView.dateEvent
        fun bind(model: FavoriteMatchModel){
            val indonesiaDateFormat = Locale("in","ID","ID")
            val dateTime = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(model.dateEvent)
            val dateTime2 = SimpleDateFormat("EEEE, dd MMMM yyyy", indonesiaDateFormat).format(dateTime)
            strHomeTeam.text = model.strHomeTeam
            strAwayTeam.text = model.strAwayTeam
            when (model.intHomeScore){
                null -> intHomeTeam.text = "0"
                else -> intHomeTeam.text = model.intHomeScore
            }
            when (model.intAwayScore){
                null -> intAwayTeam.text = "0"
                else -> intAwayTeam.text = model.intAwayScore
            }
            dateEvent.text = dateTime2
            eventBody.setOnClickListener {
                listener.onHolderClick(model.idEvent)
            }
        }
    }
    interface MyListener {
        fun onHolderClick(idEvent: String?)
    }

}