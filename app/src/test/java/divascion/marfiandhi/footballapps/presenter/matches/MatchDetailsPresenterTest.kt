package divascion.marfiandhi.footballapps.presenter.matches

import com.google.gson.Gson
import divascion.marfiandhi.footballapps.model.ApiRepository
import divascion.marfiandhi.footballapps.model.TheSportDBApi
import divascion.marfiandhi.footballapps.model.teams.Team
import divascion.marfiandhi.footballapps.model.teams.TeamResponse
import divascion.marfiandhi.footballapps.utils.TestContextProvider
import divascion.marfiandhi.footballapps.view.details.matches.MatchDetailsView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Marfiandhi on 10/11/2018.
 */
class MatchDetailsPresenterTest  {
    @Test
    fun getDetails() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val id = "1234"
        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetail(id)),
                TeamResponse::class.java
        )).thenReturn(response)
        presenter.getDetails(id)

        verify(view).showTeamDetails(teams, "1234")
        verify(view).hideLoading()
    }
    @Mock
    private
    lateinit var view: MatchDetailsView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchDetailsPresenter
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailsPresenter(view, apiRepository, gson, TestContextProvider())
    }

}