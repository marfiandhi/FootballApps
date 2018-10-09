package divascion.marfiandhi.footballapps.presenter.teams

import com.google.gson.Gson
import divascion.marfiandhi.footballapps.model.ApiRepository
import divascion.marfiandhi.footballapps.model.TheSportDBApi
import divascion.marfiandhi.footballapps.model.teams.TeamResponse
import divascion.marfiandhi.footballapps.view.main.team.TeamDetailView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Marfiandhi on 10/6/2018.
 */
class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamDetail(data.teams)
            }
        }
    }
}