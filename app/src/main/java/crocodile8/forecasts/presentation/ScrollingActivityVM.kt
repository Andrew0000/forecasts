package crocodile8.forecasts.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import crocodile8.forecasts.data.Bookmaker
import crocodile8.forecasts.data.DataProvider
import crocodile8.forecasts.data.Forecast
import crocodile8.forecasts.presentation.bookmakers.BookmakersItem
import crocodile8.forecasts.presentation.forecasts.ForecastItem
import crocodile8.forecasts.utils.onMain
import crocodile8.forecasts.utils.subscribeDefault
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo

/**
 * Created by Andrei Riik in 2020.
 */
class ScrollingActivityVM : ViewModel() {

    //TODO get with DI
    private val dataProvider = DataProvider()
    private val forecasts: MutableLiveData<List<ForecastItem>> = MutableLiveData(listOf())
    private val progressBarVisible: MutableLiveData<Boolean> = MutableLiveData(true)
    private val disposable = CompositeDisposable()

    init {
        observeData()
    }

    fun getForecasts(): LiveData<List<ForecastItem>> = forecasts

    fun getProgressBarVisible(): LiveData<Boolean> = progressBarVisible

    fun onRepeatClick(item: ForecastItem.Card) {
        //TODO show something
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun observeData() {
        Observable
            .combineLatest(
                dataProvider.getForecasts().onMain(),
                dataProvider.getBookmakers().onMain(),
                BiFunction { forecastsData: List<Forecast>, bookmakersData: List<Bookmaker> ->
                    val bookmakersMapped = bookmakersData.map { mapBookmakers(it) }
                    forecasts.value =
                        listOf(ForecastItem.Bookmakers(bookmakersMapped)) +
                                forecastsData.map { mapForecast(it) }
                    progressBarVisible.value = false
                    Unit
                }
            )
            .subscribeDefault()
            .addTo(disposable)
    }

    private fun mapForecast(src: Forecast) = with(src){
        ForecastItem.Card(
            title = title,
            time = time,
            who = who,
            coefficient = coefficient,
            belowWho = belowWho,
            description = description,
            authorName = authorName,
            authorROI = authorROI,
            repeat = repeat
        )
    }

    private fun mapBookmakers(src: Bookmaker) = with(src){
        BookmakersItem(
            title = title,
            rating = rating.toString(),
            bottomText = bottomText
        )
    }
}