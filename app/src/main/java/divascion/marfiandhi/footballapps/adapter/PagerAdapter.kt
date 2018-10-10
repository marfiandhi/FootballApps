package divascion.marfiandhi.footballapps.adapter

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by Marfiandhi on 10/6/2018.
 */
class PagerAdapter(fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

    fun addFragment(fragment: Fragment, title: String, key: String, value: String) {
        fragments.add(fragment)
        val bundle =  Bundle()
        bundle.putString(key, value)
        fragment.arguments = bundle
        titles.add(title)
    }
}