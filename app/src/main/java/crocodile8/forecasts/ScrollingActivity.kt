package crocodile8.forecasts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import crocodile8.forecasts.utils.log
import crocodile8.forecasts.utils.subscribeDefault
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    private val adapter = ForecastsFeedAdapter()
    private val scrollListener = ScrollListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setupAppBarCollapsing()
        setupMainRecycler()
    }

    private fun setupAppBarCollapsing() {
        val appBarMaxHeight = resources.getDimensionPixelSize(R.dimen.app_bar_max_height)
        val appBarMinHeight = resources.getDimensionPixelSize(R.dimen.app_bar_min_height)
        scrollListener.observeY()
            .subscribeDefault { yScroll ->
                log("yScroll: $yScroll")
                val expandFraction = (1f - (yScroll / appBarMaxHeight)).coerceIn(0f, 1f)
                val appBarHeight =
                    appBarMinHeight + (appBarMaxHeight - appBarMinHeight) * expandFraction
                mainTitleTextView.alpha = expandFraction
                appBar.updateLayoutParams {
                    height = appBarHeight.toInt()
                }
            }
    }

    private fun setupMainRecycler() {
        adapter.setItems(
            listOf(
                ForecastsFeedAdapter.Item.Bookmakers(listOf()),
                ForecastsFeedAdapter.Item.Card("1"),
                ForecastsFeedAdapter.Item.Card("2"),
                ForecastsFeedAdapter.Item.Card("3"),
                ForecastsFeedAdapter.Item.Card("4"),
                ForecastsFeedAdapter.Item.Card("5"),
                ForecastsFeedAdapter.Item.Card("6"),
                ForecastsFeedAdapter.Item.Card("7"),
                ForecastsFeedAdapter.Item.Card("8"),
            )
        )
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(scrollListener)
    }
}
