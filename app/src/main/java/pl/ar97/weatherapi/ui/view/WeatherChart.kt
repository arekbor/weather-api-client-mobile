package pl.ar97.weatherapi.ui.view

import android.content.Context
import io.data2viz.charts.chart.chart
import io.data2viz.charts.chart.discrete
import io.data2viz.charts.chart.mark.line
import io.data2viz.charts.chart.quantitative
import io.data2viz.geom.Size
import io.data2viz.viz.VizContainerView
import pl.ar97.weatherapi.data.models.Hourly
import pl.ar97.weatherapi.data.models.Weather

class WeatherChart(context: Context): VizContainerView(context) {
    private var mSize = Size(500.0, 500.0)

    fun updateChart(weather: Weather){
        val points = prepareChartPoints(weather.hourly)

        chart(points){
            size = mSize

            title = "Daily temp"

            val hour = discrete({domain.hour}){
               formatter = {this.takeLast(5)}
            }

            val temp = quantitative({domain.temp}){
                name = "Temp - Celsius"
            }

            line(hour, temp)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val size = if(w > h){
            Size(w.toDouble()/3, h.toDouble()/3)
        }else{
            Size(h.toDouble()/3, w.toDouble()/3)
        }
        mSize = size
    }

    private fun prepareChartPoints(hourly:Hourly):List<HourToTemp> {
        val array = arrayListOf<HourToTemp>()

        val hours = hourly.time
        val temps = hourly.temperature2m

        for(i in hours.indices){
            val hour = hours[i]
            val temp = temps[i]

            val hourToTemp = HourToTemp(hour, temp)
            array.add(hourToTemp)
        }
        return array.toList()
    }
}

data class HourToTemp(val hour:String, val temp:Double)