package pl.ar97.weatherapi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.ar97.weatherapi.data.models.UIData

class MainViewModel(app: Application): AndroidViewModel(app) {
    private val repository = Repository(app)
    private val _weather = MutableStateFlow<UIData>(UIData.Loading)
    val weather = _weather.asStateFlow()

    fun loadWeather(latitude:Float, longitude:Float){
        viewModelScope.launch {
            repository.loadWeather(
                latitude = latitude,
                longitude = longitude)
                .collectLatest { weather ->
                    when(weather != null){
                        true -> _weather.update { UIData.Success(weather) }
                        false -> _weather.update { UIData.Error }
                    }
                }
        }
    }
}