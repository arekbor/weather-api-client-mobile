package pl.ar97.weatherapi.data.remote

import pl.ar97.weatherapi.data.models.Weather
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    //https://api.open-meteo.com/v1/forecast?latitude=54.35&longitude=18.65&hourly=temperature_2m
    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("hourly") hourly:String = "temperature_2m"): Response<Weather>
}

object RemoteSource {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val api = retrofit.create(WeatherApi::class.java)
}