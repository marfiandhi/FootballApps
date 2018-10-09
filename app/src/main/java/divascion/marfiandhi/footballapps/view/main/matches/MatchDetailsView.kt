package divascion.marfiandhi.footballapps.view.main.matches

import divascion.marfiandhi.footballapps.model.teams.Team

/**
 * Created by Marfiandhi on 10/7/2018.
 */
interface MatchDetailsView {
    fun showTeamDetails(data: List<Team>, name: String)
    fun hideLoading()
}