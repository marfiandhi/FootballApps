package divascion.marfiandhi.footballapps.view.main.matches

import divascion.marfiandhi.footballapps.model.matches.Schedule

/**
 * Created by Marfiandhi on 10/7/2018.
 */
interface MatchesView{
    fun showLoading()
    fun hideLoading()
    fun showSchedule(data: List<Schedule>)
}