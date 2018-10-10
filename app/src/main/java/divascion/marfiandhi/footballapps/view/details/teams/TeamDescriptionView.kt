package divascion.marfiandhi.footballapps.view.details.teams

import divascion.marfiandhi.footballapps.model.teams.Team

/**
 * Created by Marfiandhi on 10/10/2018.
 */
interface TeamDescriptionView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDescription(data: List<Team>)
}