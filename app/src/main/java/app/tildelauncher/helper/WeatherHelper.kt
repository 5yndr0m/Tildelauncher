package app.tildelauncher.helper

import app.tildelauncher.data.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

suspend fun fetchWeather(city: String, apiKey: String): WeatherData? = withContext(Dispatchers.IO) {
    try {
        val encodedCity = java.net.URLEncoder.encode(city.trim(), "UTF-8")
        val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$encodedCity&appid=${apiKey.trim()}&units=metric")
        val conn = url.openConnection() as HttpURLConnection
        conn.connectTimeout = 10_000
        conn.readTimeout = 10_000
        conn.requestMethod = "GET"
        if (conn.responseCode != 200) {
            conn.disconnect()
            return@withContext null
        }
        val json = conn.inputStream.bufferedReader().readText()
        conn.disconnect()
        val obj = JSONObject(json)
        val temp = obj.getJSONObject("main").getDouble("temp").toInt()
        val condition = obj.getJSONArray("weather").getJSONObject(0).getString("main")
        WeatherData(
            temp = "$temp°",
            condition = condition,
            timestamp = System.currentTimeMillis()
        )
    } catch (e: Exception) {
        null
    }
}
