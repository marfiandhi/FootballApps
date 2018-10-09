package divascion.marfiandhi.footballapps.presenter.matches

import com.google.gson.Gson
import divascion.marfiandhi.footballapps.model.ApiRepository
import divascion.marfiandhi.footballapps.model.TheSportDBApi
import divascion.marfiandhi.footballapps.model.teams.TeamResponse
import divascion.marfiandhi.footballapps.utils.CoroutineContextProvider
import divascion.marfiandhi.footballapps.view.main.matches.MatchDetailsView
import org.jetbrains.anko.coroutines.experimental.bg
import kotlinx.coroutines.experimental.async

/**
 * Created by Marfiandhi on 10/7/2018.
 */
class MatchDetailsPresenter(private val view: MatchDetailsView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getDetails(team: String) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(team)),
                        TeamResponse::class.java
                )
            }
            view.showTeamDetails(data.await().teams, team)
            view.hideLoading()
        }
    }
}