package divascion.marfiandhi.footballapps.presenter.matches

import divascion.marfiandhi.footballapps.model.ApiRepository
import divascion.marfiandhi.footballapps.model.TheSportDBApi
import divascion.marfiandhi.footballapps.model.matches.ScheduleResponse
import kotlinx.coroutines.experimental.async
import divascion.marfiandhi.footballapps.utils.CoroutineContextProvider
import org.jetbrains.anko.coroutines.experimental.bg
import com.google.gson.Gson
import divascion.marfiandhi.footballapps.view.main.matches.MatchesView

/**
 * Created by Marfiandhi on 10/7/2018.
 */
class MatchesPresenter(private val view: MatchesView,
                       private val apiRepository: ApiRepository,
                       private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getSchedule(event: String?, id: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getSchedule(event, id)),
                        ScheduleResponse::class.java
                )
            }
            view.showSchedule(data.await().events)
            view.hideLoading()
        }
    }
}