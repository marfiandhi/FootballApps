package divascion.marfiandhi.footballapps.presenter.matches

import com.google.gson.Gson
import divascion.marfiandhi.footballapps.model.ApiRepository
import divascion.marfiandhi.footballapps.model.TheSportDBApi
import divascion.marfiandhi.footballapps.model.matches.Schedule
import divascion.marfiandhi.footballapps.model.matches.ScheduleResponse
import divascion.marfiandhi.footballapps.utils.TestContextProvider
import divascion.marfiandhi.footballapps.view.main.matches.MatchesView
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Marfiandhi on 10/11/2018.
 */
class MatchesPresenterTest {
    @Test
    fun getSchedule() {
        val events: MutableList<Schedule> = mutableListOf()
        val response = ScheduleResponse(events)
        val pastEvents = "eventspastleague.php"
        val id = "4328"
        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getSchedule(pastEvents, id)),
                ScheduleResponse::class.java
        )).thenReturn(response)
        presenter.getSchedule(pastEvents, id)

        verify(view).showLoading()
        verify(view).showSchedule(events)
        verify(view).hideLoading()
    }

    @Mock
    private
    lateinit var view: MatchesView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchesPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchesPresenter(view, apiRepository, gson, TestContextProvider())
    }
}