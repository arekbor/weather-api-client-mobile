package pl.ar97.weatherapi

import android.content.Context
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import pl.ar97.weatherapi.data.models.Weather
import pl.ar97.weatherapi.data.remote.RemoteSource

class Repository(context: Context) {
    private val api = RemoteSource.api

    fun loadWeather(latitude:Float, longitude:Float): Flow<Weather?> = flow {
        val res = api.getWeather(latitude = latitude, longitude = longitude)
        if(res.isSuccessful){
            val data = res.body()
            if(data != null){
                emit(data)
            }
        }
    }
}