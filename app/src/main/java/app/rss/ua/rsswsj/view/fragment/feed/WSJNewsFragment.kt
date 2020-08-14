package app.rss.ua.rsswsj.view.fragment.feed

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import app.rss.ua.rsswsj.KEY_RSS_URL
import app.rss.ua.rsswsj.KEY_TAB_POSITION
import app.rss.ua.rsswsj.R
import app.rss.ua.rsswsj.view.activity.WSJActivity
import app.rss.ua.rsswsj.adapter.RVNewsAdapter
import app.rss.ua.rsswsj.model.RssFeed
import kotlinx.android.synthetic.main.fragment_news.*
import org.jetbrains.anko.intentFor

/**
 * @author OTarasiuk
 * @since 13.08.2020
 */

class WSJNewsFragment : Fragment(), RVNewsAdapter.RVNewsListener {

    private lateinit var viewModel: CombinedNews

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(CombinedNews::class.java)

        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()
        loadNews()
        observeVM()
    }

    private fun initViews() {
        swipeRefreshLayout.isEnabled = false
        rvFeed.adapter = RVNewsAdapter(listOf(), this)
    }

    private fun loadNews() {
        swipeRefreshLayout.isRefreshing = true
        val tabPosition = arguments?.get(KEY_TAB_POSITION)
        when (tabPosition) {
            0 -> viewModel.loadNews()
            else -> viewModel.loadMergedFeed()
        }
    }

    private fun observeVM() {
        with(viewModel) {
            getLoading().observe(viewLifecycleOwner, Observer {
                it?.let { swipeRefreshLayout.isRefreshing = it }
            })

            getNews().observe(viewLifecycleOwner, Observer {
                it?.let {
                    (rvFeed.adapter as RVNewsAdapter).addList(it)
                }
            })
        }
    }

    override fun onFeedClicked(rssFeed: RssFeed) {
        activity?.let {
            startActivity(it.intentFor<WSJActivity>(KEY_RSS_URL to rssFeed.link))
            val result = rssFeed.title
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))

        }
    }

}
