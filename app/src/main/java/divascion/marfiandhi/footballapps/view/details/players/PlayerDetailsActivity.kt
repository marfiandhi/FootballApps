package divascion.marfiandhi.footballapps.view.details.players

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.model.players.Player
import kotlinx.android.synthetic.main.details_player.*
import org.jetbrains.anko.support.v4.onRefresh

@Suppress("DEPRECATION")
/**
 * Created by Marfiandhi on 10/10/2018.
 */
class PlayerDetailsActivity: AppCompatActivity(), PlayerDetailsView {

    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = intent.getParcelableExtra("players")
        setContentView(R.layout.details_player)
        player_swipe_refresh.setColorSchemeColors(resources.getColor(R.color.colorAccent),
                resources.getColor(android.R.color.holo_red_light),
                resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_orange_light))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadContent()
        player_swipe_refresh.onRefresh {
            loadContent()
        }
    }

    override fun hideLoading() {
        player_swipe_refresh.isRefreshing = false
    }

    override fun showLoading() {
        player_swipe_refresh.isRefreshing = true
    }

    private fun loadContent() {
        supportActionBar?.title = player.playerName
        if(player.playerFanArt==null) {
            Picasso.get().load(player.playerThumbnail).into(player_thumbnail)
        } else {
            Picasso.get().load(player.playerFanArt).into(player_thumbnail)
        }
        player_position.text = player.playerPosition
        player_description.text = player.playerDescription
        if(player.playerWeight.equals("")) {
            weight_value.text = "-"
        } else {
            weight_value.text = player.playerWeight
        }
        if(player.playerHeight.equals("")) {
            height_value.text = "-"
        } else {
            height_value.text = player.playerHeight
        }
        hideLoading()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}