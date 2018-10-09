package divascion.marfiandhi.footballapps.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import divascion.marfiandhi.footballapps.R
import divascion.marfiandhi.footballapps.R.id.*
import divascion.marfiandhi.footballapps.adapter.matches.MatchesPagerAdapter
import divascion.marfiandhi.footballapps.utils.gone
import divascion.marfiandhi.footballapps.utils.visible
import divascion.marfiandhi.footballapps.view.main.matches.FavoriteMatchesFragment
import divascion.marfiandhi.footballapps.view.main.matches.NextMatchesFragment
import divascion.marfiandhi.footballapps.view.main.matches.PrevMatchesFragment
import divascion.marfiandhi.footballapps.view.main.team.FavoriteTeamsFragment
import divascion.marfiandhi.footballapps.view.main.team.TeamsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                navigation_matches -> {
                    deleteTeamsFragment()
                    tab_layout.visible()
                    view_pager.visible()
                    setupViewPager(view_pager, "Next","Last", NextMatchesFragment(), PrevMatchesFragment())
                    tab_layout.setupWithViewPager(view_pager)
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
                    tab_layout.setupWithViewPager(view_pager)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        navigation.selectedItemId = navigation_matches
    }

    private fun setupViewPager(pager: ViewPager?, firstTitle: String, secondTitle: String, firstFragment: Fragment, secondFragment: Fragment) {
        val adapter = MatchesPagerAdapter(supportFragmentManager)

        adapter.addFragment(firstFragment, firstTitle)

        adapter.addFragment(secondFragment, secondTitle)

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
