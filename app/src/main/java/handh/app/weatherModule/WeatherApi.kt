package handh.app.weatherModule

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

interface WeatherApi {
    @GET("/forecast/7edb5229e488646f72407b66100ae674/37.8267,-122.4233")
    fun getWeather(): Call<Weather>
}

class Weather(@SerializedName("daily") val daily: Daily)
class Daily(@SerializedName("summary") val summary: String)