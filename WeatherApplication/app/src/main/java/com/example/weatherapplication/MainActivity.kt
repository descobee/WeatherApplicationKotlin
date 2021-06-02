package com.example.weatherapplication

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.tabs.TabLayout
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    val CITY: String = "lagos,ng"
    val API: String = "a4ef14af4595858cb248567dc5e63958"
    var CITY2:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WeatherTask().execute()

        val search:ImageView = findViewById(R.id.search)
        search.setOnClickListener {
            CITY2 = findViewById<EditText>(R.id.search_bar).text.toString().trim()
            weatherTaskTwo().execute()
        }
    }

    inner class weatherTaskTwo(): AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.INVISIBLE
            findViewById<ProgressBar>(R.id.loading).visibility = View.VISIBLE
            findViewById<TextView>(R.id.errorLoading).visibility = View.INVISIBLE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY2&units=metric&appid=$API")
                        .readText(Charsets.UTF_8)

            } catch (e:Exception) {
                response = null
            }
            return response
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                Log.d("log is", jsonObj.toString())
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt:Long = jsonObj.getLong("dt")
                val updateText = "Updated at " + SimpleDateFormat("dd/mm/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val minTemp = main.getString("temp_min")+"°C"
                val maxTemp = main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure") + " Pa"
                val humidity = main.getString("humidity")
                val windSpeed = wind.getString("speed") + " m/s"
                val sunrise:Long = sys.getLong("sunrise")
                val sunriseText = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                val sunset:Long = sys.getLong("sunset")
                val sunsetText = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
                val weatherDescription = weather.getString("description")
                val address = jsonObj.getString("name") + ", " + sys.getString("country")

                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updatedAt).text = updateText
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.minTemp).text = minTemp
                findViewById<TextView>(R.id.maxTemp).text = maxTemp
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.sunrise).text = sunriseText
                findViewById<TextView>(R.id.sunset).text = sunsetText
                findViewById<TextView>(R.id.status).text = weatherDescription


                //If loading was successful, the progress bar and error message should disappear
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
                findViewById<ProgressBar>(R.id.loading).visibility = View.GONE
                findViewById<TextView>(R.id.errorLoading).visibility = View.GONE

            }
            catch (e:Exception){
                findViewById<ProgressBar>(R.id.loading).visibility = View.GONE
                findViewById<TextView>(R.id.errorLoading).visibility = View.VISIBLE
            }
        }

    }

    inner class WeatherTask(): AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.INVISIBLE
            findViewById<ProgressBar>(R.id.loading).visibility = View.VISIBLE
            findViewById<TextView>(R.id.errorLoading).visibility = View.INVISIBLE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try {
               response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API")
                    .readText(Charsets.UTF_8)

            } catch (e:Exception) {
               response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                Log.d("log is", jsonObj.toString())
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val updatedAt:Long = jsonObj.getLong("dt")
                val updateText = "Updated at " + SimpleDateFormat("dd/mm/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val minTemp = main.getString("temp_min")+"°C"
                val maxTemp = main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure") + " Pa"
                val humidity = main.getString("humidity")
                val windSpeed = wind.getString("speed") + " m/s"
                val sunrise:Long = sys.getLong("sunrise")
                val sunriseText = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                val sunset:Long = sys.getLong("sunset")
                val sunsetText = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
                val weatherDescription = weather.getString("description")
                val address = jsonObj.getString("name") + ", " + sys.getString("country")

                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updatedAt).text = updateText
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.minTemp).text = minTemp
                findViewById<TextView>(R.id.maxTemp).text = maxTemp
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.sunrise).text = sunriseText
                findViewById<TextView>(R.id.sunset).text = sunsetText
                findViewById<TextView>(R.id.status).text = weatherDescription


                //If loading was successful, the progress bar and error message should disappear
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
                findViewById<ProgressBar>(R.id.loading).visibility = View.GONE
                findViewById<TextView>(R.id.errorLoading).visibility = View.GONE

            }
            catch (e:Exception){
                findViewById<ProgressBar>(R.id.loading).visibility = View.GONE
                findViewById<TextView>(R.id.errorLoading).visibility = View.VISIBLE
            }
        }
    }

}