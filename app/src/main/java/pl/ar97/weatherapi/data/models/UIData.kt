package pl.ar97.weatherapi.data.models

sealed class UIData{
    class Success(val weather: Weather):UIData()
    object Loading: UIData()
    object Error: UIData()
}
