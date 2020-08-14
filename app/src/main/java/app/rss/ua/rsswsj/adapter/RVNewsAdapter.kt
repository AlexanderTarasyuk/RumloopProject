package app.rss.ua.rsswsj.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.rss.ua.rsswsj.R
import app.rss.ua.rsswsj.model.RssFeed
import kotlinx.android.synthetic.main.rv_item_feed.view.*

/**
 * @author OTarasiuk
 * @since 13.08.2020
 *
 * @property list
 * @property listener
 */
class RVNewsAdapter(private var list: List<RssFeed>, var listener: RVNewsListener) : RecyclerView.Adapter<RVNewsAdapter.MyViewHolder>() {

    /**
     * TODO
     *
     * @param ViewGroup
     * @param ViewType
     * @return MyViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.rv_item_feed, parent, false)
        return MyViewHolder(view)
    }

    fun addList(list: List<RssFeed>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rss = list[position]

        with(holder) {
            tvTitle.text = rss.title
            tvDescription.text = rss.description
            tvDate.text = rss.date
        }

        holder.layoutRoot.setOnClickListener { listener.onFeedClicked(rss) }
    }

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var layoutRoot = view.layoutRoot
        var tvTitle = view.tvTitle
        var tvDescription = view.tvDescription
        var tvDate = view.tvDate
    }

    interface RVNewsListener {
        fun onFeedClicked(rssFeed: RssFeed)
    }

}