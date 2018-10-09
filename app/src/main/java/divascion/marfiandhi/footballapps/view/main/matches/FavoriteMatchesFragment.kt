package divascion.marfiandhi.footballapps.view.main.matches

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.adapter.matches.FavoriteMatchesAdapter
import divascion.marfiandhi.footballapps.database.matches.database
import divascion.marfiandhi.footballapps.model.matches.Favorite
import divascion.marfiandhi.footballapps.model.matches.Schedule
import divascion.marfiandhi.footballapps.utils.invisible
import divascion.marfiandhi.footballapps.utils.visible
import divascion.marfiandhi.footballapps.view.details.matches.MatchDetailsActivity
import kotlinx.android.synthetic.main.fragment_favorite_matches.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

@Suppress("DEPRECATION")
/**
 * Created by Marfiandhi on 10/7/2018.
 */
class FavoriteMatchesFragment: Fragment(), MatchesView {
    private var favorites: MutableList<Favorite> = mutableListOf()

    private lateinit var adapterFavorite : FavoriteMatchesAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        favorite_swipe_refresh.setColorSchemeColors(resources.getColor(R.color.colorAccent),
                resources.getColor(android.R.color.holo_red_light),
                resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_orange_light))

        recycler_favorite.layoutManager = LinearLayoutManager(activity!!)

        adapterFavorite = FavoriteMatchesAdapter(activity!!, favorites) {
            startActivity(intentFor<MatchDetailsActivity>(
                    "extra_item_favorite" to it,
                    "boolean" to false))
        }

        recycler_favorite.adapter = adapterFavorite
        showFavorite()

        favorite_swipe_refresh.onRefresh {
            favorites.clear()
            showFavorite()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite_matches, container, false)
    }

    override fun hideLoading() {
        favorite_progress_bar.invisible()
    }

    override fun showSchedule(data: List<Schedule>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        favorite_progress_bar.visible()
    }

    private fun showFavorite() {
        context?.database?.use {
            favorite_swipe_refresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapterFavorite.notifyDataSetChanged()
        }
    }

}