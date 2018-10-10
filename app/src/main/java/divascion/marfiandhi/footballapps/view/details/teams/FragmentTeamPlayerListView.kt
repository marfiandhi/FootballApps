package divascion.marfiandhi.footballapps.view.details.teams

import divascion.marfiandhi.footballapps.model.players.Player

/**
 * Created by Marfiandhi on 10/10/2018.
 */
interface FragmentTeamPlayerListView {
    fun showLoading()
    fun hideLoading()
    fun showTeamPlayers(data: List<Player>)
}