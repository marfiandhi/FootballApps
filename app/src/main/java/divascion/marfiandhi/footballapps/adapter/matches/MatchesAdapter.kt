package divascion.marfiandhi.footballapps.adapter.matches

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.model.matches.Schedule
import kotlinx.android.synthetic.main.matches_list.view.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
/**
 * Created by Marfiandhi on 10/7/2018.
 */
class MatchesAdapter(private val context: Context, private val events: List<Schedule>, private val listener: (Schedule) -> Unit)
    : RecyclerView.Adapter<MatchesAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.matches_list, parent, false))

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount(): Int = events.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

        @SuppressLint("SimpleDateFormat")
        fun bindItem(events: Schedule, listener: (Schedule) -> Unit) {
            itemView.teamA.text = events.home
            itemView.teamB.text = events.away
            if(events.homeScore!=null){
                itemView.scoreTeamA.text = events.homeScore.toString()
                itemView.scoreTeamB.text = events.awayScore.toString()
            } else {
                itemView.scoreTeamA.text = ""
                itemView.scoreTeamB.text = ""
            }
            var dateEvent = SimpleDateFormat("EEE, dd MMM yyyy").format(events.date)
            val timeStart = SimpleDateFormat("HH:mm").parse(events.strTime)
            val date = SimpleDateFormat("HH:mm:ssZ").parse(events.strTime)
            val timeEvent = SimpleDateFormat("HH:mm").format(date)
            val timeParsed = SimpleDateFormat("H").parse(timeEvent)
            if(timeParsed.hours<=8 && timeStart.hours>=15) {
                val calendar = Calendar.getInstance()
                calendar.time = SimpleDateFormat("EEE, dd MMM yyyy").parse(dateEvent)
                calendar.add(Calendar.DATE, 1)
                dateEvent = SimpleDateFormat("EEE, dd MMM yyyy").format(calendar.time)
            }
            itemView.time.text = timeEvent
            itemView.date.text = dateEvent

            itemView.setOnClickListener { listener(events) }

        }
    }

}