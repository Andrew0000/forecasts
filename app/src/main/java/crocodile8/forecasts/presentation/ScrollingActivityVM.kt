package crocodile8.forecasts.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import crocodile8.forecasts.data.DataProvider
import crocodile8.forecasts.data.Forecast
import crocodile8.forecasts.utils.subscribeDefault
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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
        observeForecasts()
    }

    fun getForecasts(): LiveData<List<ForecastItem>> = forecasts

    fun getProgressBarVisible(): LiveData<Boolean> = progressBarVisible

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private fun observeForecasts() {
        dataProvider.getForecasts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeDefault { forecastsData ->
                forecasts.postValue(
                    listOf(ForecastItem.Bookmakers(listOf())) +
                            forecastsData.map { mapForecast(it) }
                )
                progressBarVisible.postValue(false)
            }
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
}