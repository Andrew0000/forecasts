package crocodile8.forecasts.utils

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by Andrei Riik in 2020.
 */

//TODO Need special logger

fun log(text: String) {
    Log.d("Forecasts", text)
}

inline fun <T> Observable<T>.subscribeDefault(crossinline block: (T) -> Unit = {}): Disposable =
    this.subscribe({ block(it) }, {
        Log.e("", "", it)
    })

fun <T> Observable<T>.onMain(): Observable<T> = observeOn(AndroidSchedulers.mainThread())