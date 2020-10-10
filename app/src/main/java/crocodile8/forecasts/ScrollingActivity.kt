package crocodile8.forecasts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import crocodile8.forecasts.data.DataProvider
import crocodile8.forecasts.data.Forecast
import crocodile8.forecasts.utils.subscribeDefault
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    private val adapter = ForecastsFeedAdapter()
    private val scrollListener = ScrollListener()
    private val dataProvider = DataProvider() //TODO DI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setupAppBarCollapsing()
        setupMainRecycler()
        observeForecasts()
    }

    // Use own implementation instead of default CollapsingToolbar
    // for more control
    private fun setupAppBarCollapsing() {
        val appBarMaxHeight = resources.getDimensionPixelSize(R.dimen.app_bar_max_height)
        val appBarMinHeight = resources.getDimensionPixelSize(R.dimen.app_bar_min_height)
        val workHeight = appBarMaxHeight - appBarMinHeight
        scrollListener.observeY()
            .subscribeDefault { yScroll ->
                val expandFraction = (1f - (yScroll / workHeight)).coerceIn(0f, 1f)
                mainTitleTextView.alpha = expandFraction
                val appBarHeight = (appBarMinHeight + workHeight * expandFraction).toInt()
                if (appBarHeight != appBar.height) {
                    appBar.updateLayoutParams {
                        height = appBarHeight
                    }
                }
            }
    }

    private fun setupMainRecycler() {
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun observeForecasts() {
        dataProvider.getForecasts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeDefault { forecasts ->
                adapter.setItems(
                    listOf(ForecastsFeedAdapter.Item.Bookmakers(listOf())) +
                            forecasts.map { mapForecast(it) }
                )
            }
        //TODO disposable
    }

    private fun mapForecast(src: Forecast) = with(src){
        ForecastsFeedAdapter.Item.Card(
            title = title,
            time = time,
            who = who,
            coefficient = coefficient,
            belowWho = belowWho,
            description = description,
            authorName = authorName,
            authorROI = authorROI
        )
    }
}
