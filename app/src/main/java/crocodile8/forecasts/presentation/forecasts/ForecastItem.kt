package crocodile8.forecasts.presentation.forecasts

import crocodile8.forecasts.presentation.bookmakers.BookmakersItem

sealed class ForecastItem(val type: Int) {

    data class Card(
        val title: String,
        val time: String,
        val who: String,
        val coefficient: String,
        val belowWho: String,
        val description: String,
        val authorName: String,
        val authorROI: String,
        val repeat: String
    ) : ForecastItem(
        type = ForecastsFeedAdapter.TYPE_CARD
    )

    data class Bookmakers(
        val items: List<BookmakersItem>
    ) : ForecastItem(
        type = ForecastsFeedAdapter.TYPE_BOOKMAKERS
    )

    object CardsTitle : ForecastItem(
        type = ForecastsFeedAdapter.TYPE_CARD_TITLE
    )
}