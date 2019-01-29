package handh.app.weatherModule

import io.reactivex.Single

interface WeatherModule {

    fun requestWeatherSummary(): Single<String>
}