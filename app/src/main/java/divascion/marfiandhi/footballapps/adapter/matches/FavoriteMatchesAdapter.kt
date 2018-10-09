package divascion.marfiandhi.footballapps.adapter.matches

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.model.matches.Favorite
import kotlinx.android.synthetic.main.matches_list.view.*

/**
 * Created by Marfiandhi on 10/7/2018.
 */
class FavoriteMatchesAdapter(private val context: Context, private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteMatchesAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
            FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.matches_list, parent, false))

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
            itemView.teamA.text = favorite.home
            itemView.teamB.text = favorite.away
            if(favorite.homeScore!="null"){
                itemView.scoreTeamA.text = favorite.homeScore.toString()
                itemView.scoreTeamB.text = favorite.awayScore.toString()
            } else {
                itemView.scoreTeamA.text = ""
                itemView.scoreTeamB.text = ""
            }
            itemView.date.text = favorite.date
            itemView.time.text = favorite.strTime

            itemView.setOnClickListener { listener(favorite) }
        }
    }

}