package pl.ar97.weatherapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import pl.ar97.weatherapi.data.models.UIData
import pl.ar97.weatherapi.ui.view.WeatherChart

class MainActivity : ComponentActivity() {
    private val mainVm by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val data by mainVm.weather.collectAsState()
            var key by remember{
                mutableStateOf(1)
            }

            LaunchedEffect(key1 = key){
                mainVm.loadWeather(51.2f, 16.0f)
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AndroidView(
                    factory = {context ->  WeatherChart(context)},
                    update = {
                        when(data){
                            is UIData.Success -> it.updateChart((data as UIData.Success).weather)
                            else -> {}
                        }
                    }
                )
            }
        }
    }
}
