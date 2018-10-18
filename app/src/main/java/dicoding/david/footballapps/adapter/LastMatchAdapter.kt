package dicoding.david.footballapps.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dicoding.david.footballapps.R.layout.match_list
import dicoding.david.footballapps.model.LastMatchModel
import kotlinx.android.synthetic.main.match_list.view.*
import java.text.SimpleDateFormat
import java.util.*


class LastMatchAdapter(private val dataList: ArrayList<LastMatchModel>?, private val listener: MyListener) : RecyclerView.Adapter<LastMatchAdapter.LastMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMatchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(match_list, parent, false)
        return LastMatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: LastMatchViewHolder, position: Int) {
        dataList?.let {
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    inner class LastMatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventBody: ConstraintLayout = itemView.eventBody
        val strHomeTeam: TextView = itemView.teamHome
        val strAwayTeam: TextView = itemView.teamAway
        val intHomeTeam: TextView = itemView.scoreHome
        val intAwayTeam: TextView = itemView.scoreAway
        val dateEvent: TextView = itemView.dateEvent
        fun bind(model: LastMatchModel){
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