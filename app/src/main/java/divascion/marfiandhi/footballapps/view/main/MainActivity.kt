package divascion.marfiandhi.footballapps.view.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.R.id.*
import divascion.marfiandhi.footballapps.adapter.PagerAdapter
import divascion.marfiandhi.footballapps.utils.gone
import divascion.marfiandhi.footballapps.utils.visible
import divascion.marfiandhi.footballapps.view.main.matches.FavoriteMatchesFragment
import divascion.marfiandhi.footballapps.view.main.matches.NextMatchesFragment
import divascion.marfiandhi.footballapps.view.main.matches.PrevMatchesFragment
import divascion.marfiandhi.footballapps.view.main.team.FavoriteTeamsFragment
import divascion.marfiandhi.footballapps.view.main.team.TeamsFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        tab_layout.setupWithViewPager(view_pager)

        tab_layout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            private val mSelectedListeners = ArrayList<TabLayout.OnTabSelectedListener>()
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager.setCurrentItem(tab.position, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                for (i in mSelectedListeners.indices.reversed()) {
                    mSelectedListeners[i].onTabUnselected(tab)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                try {
                    val recyclerNext: RecyclerView = findViewById(recycler_next)
                    val recyclerLast: RecyclerView = findViewById(recycler_last)
                    recyclerNext.smoothScrollToPosition(0)
                    recyclerLast.smoothScrollToPosition(0)
                } catch(f: Exception) {
                    Log.e("Tab Reselected", f.toString())
                }
                try {
                    val recyclerFavoriteMatch: RecyclerView = findViewById(recycler_favorite)
                    val recyclerFavoriteTeam: RecyclerView = findViewById(recycler_favorite_team)

                    recyclerFavoriteMatch.smoothScrollToPosition(0)
                    recyclerFavoriteTeam.smoothScrollToPosition(0)
                } catch(e: Exception) {
                    Log.e("Tab Reselected", e.toString())
                }
            }
        })

        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                navigation_matches -> {
                    deleteTeamsFragment()
                    tab_layout.visible()
                    view_pager.visible()
                    setupViewPager(view_pager, "Next","Last", NextMatchesFragment(), PrevMatchesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                navigation_teams -> {
                    tab_layout.gone()
                    view_pager.gone()
                    loadTeamsFragment(savedInstanceState)
                    return@setOnNavigationItemSelectedListener true
                }
                navigation_favorites -> {
                    deleteTeamsFragment()
                    tab_layout.visible()
                    view_pager.visible()
                    setupViewPager(view_pager, "Matches","Teams", FavoriteMatchesFragment(), FavoriteTeamsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        navigation.selectedItemId = navigation_matches
    }

    private fun setupViewPager(pager: ViewPager?, firstTitle: String, secondTitle: String, firstFragment: Fragment, secondFragment: Fragment) {
        val adapter = PagerAdapter(supportFragmentManager)

        adapter.addFragment(firstFragment, firstTitle,"","")

        adapter.addFragment(secondFragment, secondTitle,"","")

        pager?.adapter = adapter
    }

    private fun loadTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame, TeamsFragment(), TeamsFragment::class.simpleName)
                    .commit()
        }
    }

    private fun deleteTeamsFragment() {
        if(supportFragmentManager.fragments!=null && supportFragmentManager.fragments.size>0) {
            for (i in 0 until supportFragmentManager.fragments.size) {
                val mFragment = supportFragmentManager.fragments[i]
                if (mFragment != null) {
                    supportFragmentManager.beginTransaction().remove(mFragment).commit()
                }
            }
        }
    }
}
