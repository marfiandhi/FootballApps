package divascion.marfiandhi.footballapps.view.main.team

import divascion.marfiandhi.footballapps.model.teams.Team

/**
 * Created by Marfiandhi on 10/6/2018.
 */
interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}