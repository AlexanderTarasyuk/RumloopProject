package app.rss.ua.rsswsj.view.fragment.tabs

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.rss.ua.rsswsj.KEY_TAB_POSITION

import app.rss.ua.rsswsj.R
import app.rss.ua.rsswsj.view.fragment.feed.WSJNewsFragment
import kotlinx.android.synthetic.main.fragment_wsj.*

/**
 * @author OTarasiuk
 * @since 13.08.2020
 */
class NewsFragment : Fragment(), TabLayout.OnTabSelectedListener {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_wsj, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadFeedFragment(0)
        with(tabLayout) {
            setTabTextColors(Color.BLACK, Color.WHITE)
            addTab(tabLayout.newTab().setText("WSJ News"))
            addTab(tabLayout.newTab().setText("Combined News"))
            addOnTabSelectedListener(this@NewsFragment)
        }
    }


    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let { loadFeedFragment(it.position) }
    }

    private fun loadFeedFragment(tabPosition: Int) {
        val fragment = WSJNewsFragment()
        val bundle = Bundle()
        bundle.putInt(KEY_TAB_POSITION, tabPosition)
        fragment.arguments = bundle

        val transaction = parentFragmentManager.beginTransaction()
        transaction.run {
            replace(R.id.frame_child_container, fragment)
            addToBackStack(null)
            commit()
        }

    }

}
