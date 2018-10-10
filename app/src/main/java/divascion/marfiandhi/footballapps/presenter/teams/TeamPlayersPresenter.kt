package divascion.marfiandhi.footballapps.presenter.teams

import com.google.gson.Gson
import divascion.marfiandhi.footballapps.model.ApiRepository
import divascion.marfiandhi.footballapps.model.TheSportDBApi
import divascion.marfiandhi.footballapps.model.players.PlayerResponse
import divascion.marfiandhi.footballapps.utils.CoroutineContextProvider
import divascion.marfiandhi.footballapps.view.details.teams.FragmentTeamPlayerListView
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by Marfiandhi on 10/10/2018.
 */
class TeamPlayersPresenter(private val view: FragmentTeamPlayerListView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getPlayers(id: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayer(id)),
                        PlayerResponse::class.java
                )
            }
            view.showTeamPlayers(data.await().player)
            view.hideLoading()
        }
    }
}