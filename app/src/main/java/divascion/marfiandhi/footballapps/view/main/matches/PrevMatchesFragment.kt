package divascion.marfiandhi.footballapps.view.main.matches

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.adapter.matches.MatchesAdapter
import divascion.marfiandhi.footballapps.model.ApiRepository
import divascion.marfiandhi.footballapps.model.matches.Schedule
import divascion.marfiandhi.footballapps.presenter.matches.MatchesPresenter
import divascion.marfiandhi.footballapps.utils.invisible
import divascion.marfiandhi.footballapps.utils.visible
import divascion.marfiandhi.footballapps.view.details.matches.MatchDetailsActivity
import kotlinx.android.synthetic.main.fragment_prev_matches.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

@Suppress("DEPRECATION")
/**
 * Created by Marfiandhi on 10/6/2018.
 */
class PrevMatchesFragment: Fragment(), MatchesView, SearchView.OnQueryTextListener {

    private var matches: MutableList<Schedule> = mutableListOf()
    private var searchMatches: MutableList<Schedule> = ArrayList()

    private lateinit var presenter: MatchesPresenter
    private lateinit var adapter : MatchesAdapter

    private val pastEvents = "eventspastleague.php"
    private lateinit var presenterEvents: String
    private lateinit var leagueName : String
    private var idLeague = "4328"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.my_spinner_dropdown_item, spinnerItems)
        val popupBackground = ColorDrawable(Color.DKGRAY)

        setHasOptionsMenu(true)

        matches_spinner_last.setPopupBackgroundDrawable(popupBackground)
        matches_spinner_last.adapter = spinnerAdapter

        last_swipe_refresh.setColorSchemeColors(resources.getColor(R.color.colorAccent),
                resources.getColor(android.R.color.holo_red_light),
                resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_orange_light))

        recycler_last.layoutManager = LinearLayoutManager(activity!!)

        adapter = MatchesAdapter(activity!!, matches){
            startActivity(intentFor<MatchDetailsActivity>(
                    "extra_item" to it,
                    "boolean" to true))
        }

        recycler_last.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchesPresenter(this, request, gson)
        presenterEvents = pastEvents
        presenter.getSchedule(presenterEvents, idLeague)

        matches_spinner_last.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = matches_spinner_last.selectedItem.toString()
                when(leagueName) {
                    "English Premier League" ->  idLeague = "4328"
                    "English League Championship" -> idLeague = "4329"
                    "German Bundesliga" -> idLeague = "4331"
                    "Italian Serie A" -> idLeague = "4332"
                    "French Ligue 1" -> idLeague = "4334"
                    "Spanish La Liga" -> idLeague = "4335"
                    else -> Log.e("ID", idLeague)
                }
                presenter.getSchedule(presenterEvents,idLeague)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        last_swipe_refresh.onRefresh {
            presenter.getSchedule(presenterEvents, idLeague)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_prev_matches, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search, menu)
        val searchItem = menu?.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Search..."

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        matches.clear()
        if(newText!!.isNotEmpty()) {
            val search = newText.toLowerCase()
            searchMatches.forEach{
                if(it.home!!.toLowerCase().contains(search)|| it.away!!.toLowerCase().contains(search)) {
                    matches.add(it)
                }
            }
        } else {
            matches.addAll(searchMatches)
        }
        adapter.notifyDataSetChanged()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun hideLoading() {
        last_progress_bar.invisible()
    }

    override fun showSchedule(data: List<Schedule>) {
        last_swipe_refresh.isRefreshing = false
        searchMatches.clear()
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
        searchMatches.addAll(matches)
    }

    override fun showLoading() {
        last_progress_bar.visible()
    }
}