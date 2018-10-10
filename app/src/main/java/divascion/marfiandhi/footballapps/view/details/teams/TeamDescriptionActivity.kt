package divascion.marfiandhi.footballapps.view.details.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.model.ApiRepository
import divascion.marfiandhi.footballapps.model.teams.Team
import divascion.marfiandhi.footballapps.presenter.teams.TeamDetailPresenter
import kotlinx.android.synthetic.main.fragment_team_description.*
import org.jetbrains.anko.support.v4.onRefresh

@Suppress("DEPRECATION")
/**
 * Created by Marfiandhi on 10/10/2018.
 */
class TeamDescriptionActivity: Fragment(), TeamDescriptionView{

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teamId: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        team_description_swipe.setColorSchemeColors(resources.getColor(R.color.colorAccent),
                resources.getColor(android.R.color.holo_red_light),
                resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_orange_light))

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(teamId)

        team_description_swipe.onRefresh {
            presenter.getTeamDetail(teamId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        teamId = arguments?.getString("id").toString()
        return inflater.inflate(R.layout.fragment_team_description, container, false)
    }

    override fun hideLoading() {
        team_description_swipe.isRefreshing = false
    }

    override fun showLoading() {
        team_description_swipe.isRefreshing = true
    }

    override fun showTeamDescription(data: List<Team>) {
        team_description_text.text = data[0].teamDescription
    }
}