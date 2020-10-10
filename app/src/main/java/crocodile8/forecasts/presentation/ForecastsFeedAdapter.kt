package crocodile8.forecasts.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import crocodile8.forecasts.R
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
            TYPE_CARD -> CardVH(
                inflater.inflate(
                    R.layout.forecast_feed_item,
                    parent,
                    false
                )
            )
            TYPE_BOOKMAKERS -> BookmakersVH(
                inflater.inflate(
                    R.layout.forecast_bookmakers_item,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is CardVH -> bindCard(holder, item as ForecastItem.Card)
            is BookmakersVH -> {
                item as ForecastItem.Bookmakers

            }
        }
    }

    private fun bindCard(
        holder: CardVH,
        item: ForecastItem.Card
    ) {
        holder.title.text = item.title
        holder.time.text = item.time
        holder.who.text = item.who
        holder.coefficient.text = item.coefficient
        holder.belowWho.text = item.belowWho
        holder.description.text = item.description
        holder.authorName.text = item.authorName
        holder.authorROI.text = item.authorROI
        holder.bottomText.apply {
            text = item.repeat
            setOnClickListener { onRepeatClick(item) }
        }
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

    class BookmakersVH(view: View) : RecyclerView.ViewHolder(view)
}

