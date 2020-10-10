package crocodile8.forecasts.presentation.forecasts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import crocodile8.forecasts.R
import crocodile8.forecasts.presentation.bookmakers.BookmakersAdapter
import kotlinx.android.synthetic.main.forecast_bookmakers_item.view.*
import kotlinx.android.synthetic.main.forecast_feed_item.view.*

//TODO Improvement: Use any kind of delegates-based adapter

class ForecastsFeedAdapter(
    private val onRepeatClick: (ForecastItem.Card) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_CARD = 1
        const val TYPE_BOOKMAKERS = 2
    }

    private var items = listOf<ForecastItem>()

    fun setItems(newItems: List<ForecastItem>) {
        items = newItems
        notifyDataSetChanged()
        //TODO Improvement: Use Diff utils if needed
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_CARD -> createCardViewHolder(inflater, parent)
            TYPE_BOOKMAKERS -> createBookmakersViewHolder(inflater, parent)
            else -> throw IllegalArgumentException()
        }
    }

    private fun createBookmakersViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) =
        BookmakersVH(
            inflater.inflate(
                R.layout.forecast_bookmakers_item,
                parent,
                false
            )
        ).also {
            it.recycler.layoutManager =
                LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
            it.recycler.adapter = it.adapter
        }

    private fun createCardViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) =
        CardVH(
            inflater.inflate(
                R.layout.forecast_feed_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is CardVH -> bindCard(holder, item as ForecastItem.Card)
            is BookmakersVH -> bindBookmakers(holder, item as ForecastItem.Bookmakers)
        }
    }

    private fun bindCard(
        holder: CardVH,
        item: ForecastItem.Card
    ) {
        holder.apply {
            title.text = item.title
            time.text = item.time
            who.text = item.who
            coefficient.text = item.coefficient
            belowWho.text = item.belowWho
            description.text = item.description
            authorName.text = item.authorName
            authorROI.text = item.authorROI
            bottomText.apply {
                text = item.repeat
                setOnClickListener { onRepeatClick(item) }
            }
        }
    }

    private fun bindBookmakers(
        holder: BookmakersVH,
        item: ForecastItem.Bookmakers
    ) {
        holder.adapter.setItems(item.items)
    }

    class CardVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.title
        val time: TextView = view.time
        val who: TextView = view.who
        val coefficient: TextView = view.coefficient
        val belowWho: TextView = view.belowWho
        val description: TextView = view.description
        val authorName: TextView = view.authorName
        val authorROI: TextView = view.authorROI
        val bottomText: TextView = view.bottomText
    }

    class BookmakersVH(view: View) : RecyclerView.ViewHolder(view) {
        val recycler = view.ratingsRecyclerView
        val adapter =
            BookmakersAdapter()
    }
}

