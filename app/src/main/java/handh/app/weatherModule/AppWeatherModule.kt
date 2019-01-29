package handh.app.weatherModule

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AppWeatherModule : WeatherModule {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl("https://api.darksky.net/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    override fun requestWeatherSummary(): Single<String> {
        return Single.create<String> {
            retrofit.create(WeatherApi::class.java)
                    .getWeather()
                    .enqueue(object : Callback<Weather> {
                        override fun onFailure(call: Call<Weather>, t: Throwable) = it.onError(t)

                        override fun onResponse(call: Call<Weather>, response: Response<Weather>) = it.onSuccess(response.body()!!.daily.summary)
                    })
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}