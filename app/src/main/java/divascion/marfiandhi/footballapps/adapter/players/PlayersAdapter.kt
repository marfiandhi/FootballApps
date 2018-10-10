package divascion.marfiandhi.footballapps.adapter.players

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.model.players.Player
import kotlinx.android.synthetic.main.players_list.view.*

/**
 * Created by Marfiandhi on 10/10/2018.
 */
class PlayersAdapter (private val context: Context, private val players: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.players_list, parent, false))

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    override fun getItemCount(): Int = players.size

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(players: Player, listener: (Player) -> Unit) {
            itemView.player_name.text = players.playerName
            itemView.position.text = players.playerPosition
            if(players.playerCutOut!=null) {
                Picasso.get().load(players.playerCutOut).into(itemView.player_cutout)
            } else {
                Picasso.get().load("https://vignette.wikia.nocookie.net/cswikia/images/2/20/Profile-blank-male.png/revision/latest?cb=20150701175801").into(itemView.player_cutout)
            }
            itemView.setOnClickListener { listener(players) }

        }
    }

}