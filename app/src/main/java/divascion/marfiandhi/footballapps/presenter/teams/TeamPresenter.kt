package divascion.marfiandhi.footballapps.presenter.teams

import divascion.marfiandhi.footballapps.model.TheSportDBApi
import divascion.marfiandhi.footballapps.model.teams.TeamResponse
import divascion.marfiandhi.footballapps.model.ApiRepository
import org.jetbrains.anko.coroutines.experimental.bg
import kotlinx.coroutines.experimental.async
import com.google.gson.Gson
import divascion.marfiandhi.footballapps.utils.CoroutineContextProvider
import divascion.marfiandhi.footballapps.view.main.team.TeamsView

/**
 * Created by Marfiandhi on 10/6/2018.
 */
class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeams(league)),
                        TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}