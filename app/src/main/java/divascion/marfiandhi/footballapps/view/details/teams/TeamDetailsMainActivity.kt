package divascion.marfiandhi.footballapps.view.details.teams

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.adapter.PagerAdapter
import divascion.marfiandhi.footballapps.database.teams.database
import divascion.marfiandhi.footballapps.model.teams.Favorite
import divascion.marfiandhi.footballapps.model.teams.Team
import divascion.marfiandhi.footballapps.utils.snackBarShow
import kotlinx.android.synthetic.main.team_details_main.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select


/**
 * Created by Marfiandhi on 10/10/2018.
 */
class TeamDetailsMainActivity: AppCompatActivity() {

    private var menuItem: Menu? = null

    private lateinit var item: Team
    private lateinit var itemFavorite: Favorite

    private var check = true
    private lateinit var teamId: String

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        check = intent.getBooleanExtra("favorite", false)
        setContentView(R.layout.team_details_main)

        if(check) {
            itemFavorite = intent.getParcelableExtra("team")
            teamId = itemFavorite.teamId.toString()
            loadContentFavoriteToolbar(itemFavorite)
        } else {
            item = intent.getParcelableExtra("team")
            teamId = item.teamId.toString()
            loadContentToolbar(item)
        }

        setSupportActionBar(toolbar_team)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        toolbar_team.setNavigationOnClickListener {
            onBackPressed()
        }

        tab_layout_team.setupWithViewPager(view_pager_team)

        setupViewPager(view_pager_team,"Overview", "Players", TeamDescriptionActivity(), FragmentTeamPlayersListActivity())

        favoriteState()
    }

    private fun setupViewPager(pager: ViewPager?, firstTitle: String, secondTitle: String, firstFragment: Fragment, secondFragment: Fragment) {
        val adapter = PagerAdapter(supportFragmentManager)

        adapter.addFragment(firstFragment, firstTitle,"id", teamId)

        adapter.addFragment(secondFragment, secondTitle,"id", teamId)

        pager?.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun loadContentToolbar(data: Team) {
        Picasso.get().load(data.teamBadge).into(team_logo)
        team_name.text = data.teamName
        team_formed_year.text = data.teamFormedYear
        team_stadium.text = data.teamStadium
    }

    private fun loadContentFavoriteToolbar(data: Favorite) {
        Picasso.get().load(data.teamBadge).into(team_logo)
        team_name.text = data.teamName
        team_formed_year.text = data.teamFormedYear
        team_stadium.text = data.teamStadium
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to teamId)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.TEAM_ID to item.teamId,
                        Favorite.TEAM_NAME to item.teamName,
                        Favorite.TEAM_BADGE to item.teamBadge,
                        Favorite.TEAM_FORMED_YEAR to item.teamFormedYear,
                        Favorite.TEAM_STADIUM to item.teamStadium)
            }
            snackBarShow(team_detail, "Added to favorite")
        } catch (e: SQLiteConstraintException){
            snackBarShow(team_detail, e.localizedMessage)
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(TEAM_ID = {id})","id" to teamId)
            }
            snackBarShow(team_detail, "Removed from favorite")
        } catch (e: SQLiteConstraintException){
            snackBarShow(team_detail, e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}