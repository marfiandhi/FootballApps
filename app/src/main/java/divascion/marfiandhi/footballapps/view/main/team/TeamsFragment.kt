package divascion.marfiandhi.footballapps.view.main.team

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.R.array.league
import divascion.marfiandhi.footballapps.adapter.teams.TeamsAdapter
import divascion.marfiandhi.footballapps.model.ApiRepository
import divascion.marfiandhi.footballapps.model.teams.Team
import divascion.marfiandhi.footballapps.presenter.teams.TeamsPresenter
import divascion.marfiandhi.footballapps.utils.invisible
import divascion.marfiandhi.footballapps.utils.visible
import divascion.marfiandhi.footballapps.view.details.teams.TeamDetailsMainActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * Created by Marfiandhi on 10/6/2018.
 */
class TeamsFragment : Fragment(), AnkoComponent<Context>, TeamsView, SearchView.OnQueryTextListener {

    private var teams: MutableList<Team> = mutableListOf()
    private var searchTeams: MutableList<Team> = ArrayList()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var spinner: Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.my_spinner_dropdown_item, spinnerItems)
        val popupBackground = ColorDrawable(Color.BLACK)
        spinner.setPopupBackgroundDrawable(popupBackground)
        spinner.adapter = spinnerAdapter
        setHasOptionsMenu(true)

        adapter = TeamsAdapter(teams) {
            ctx.startActivity<TeamDetailsMainActivity>(
                    "team" to it,
                    "favorite" to false)
        }
        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }


    override fun createView(ui: AnkoContext<Context>): View = with(ui){

        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(56)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.spinner
            }

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.list_team
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }

    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        searchTeams.clear()
        teams.clear()
        teams.addAll(data)
        searchTeams.addAll(teams)
        adapter.notifyDataSetChanged()
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
        teams.clear()
        if(newText!!.isNotEmpty()) {
            val search = newText.toLowerCase()
            searchTeams.forEach{
                if(it.teamName!!.toLowerCase().contains(search)) {
                    teams.add(it)
                }
            }
        } else {
            teams.addAll(searchTeams)
        }
        adapter.notifyDataSetChanged()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }
}