package divascion.marfiandhi.footballapps.view.details.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.adapter.players.PlayersAdapter
import divascion.marfiandhi.footballapps.model.ApiRepository
import divascion.marfiandhi.footballapps.model.players.Player
import divascion.marfiandhi.footballapps.presenter.teams.TeamPlayersPresenter
import divascion.marfiandhi.footballapps.view.details.players.PlayerDetailsActivity
import kotlinx.android.synthetic.main.fragment_team_players_list.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

@Suppress("DEPRECATION")
class FragmentTeamPlayersListActivity : Fragment(), FragmentTeamPlayerListView {

    private var players : MutableList<Player> = mutableListOf()
    private lateinit var presenter: TeamPlayersPresenter
    private lateinit var teamId: String

    private lateinit var adapter : PlayersAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        player_list_swipe_refresh.setColorSchemeColors(resources.getColor(R.color.colorAccent),
                resources.getColor(android.R.color.holo_red_light),
                resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_orange_light))

        recycler_player.layoutManager = LinearLayoutManager(activity!!)

        adapter = PlayersAdapter(activity!!, players){
            startActivity(intentFor<PlayerDetailsActivity>(
                    "players" to it))
        }
        recycler_player.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPlayersPresenter(this, request, gson)
        presenter.getPlayers(teamId)

        player_list_swipe_refresh.onRefresh {
            presenter.getPlayers(teamId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        teamId = arguments?.getString("id").toString()
        return inflater.inflate(R.layout.fragment_team_players_list, container, false)
    }

    override fun hideLoading() {
        player_list_swipe_refresh.isRefreshing = false
    }

    override fun showLoading() {
        player_list_swipe_refresh.isRefreshing = true
    }

    override fun showTeamPlayers(data: List<Player>) {
        player_list_swipe_refresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
