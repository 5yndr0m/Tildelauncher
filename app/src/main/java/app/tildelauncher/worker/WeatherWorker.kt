package app.tildelauncher.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.tildelauncher.data.Prefs
import app.tildelauncher.helper.fetchWeather

class WeatherWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val prefs = Prefs(applicationContext)
        if (!prefs.atAGlanceEnabled || !prefs.weatherEnabled) return Result.success()
        if (prefs.weatherCity.isBlank() || prefs.weatherApiKey.isBlank()) return Result.success()
        val data = fetchWeather(prefs.weatherCity, prefs.weatherApiKey) ?: return Result.retry()
        prefs.weatherCachedTemp = data.temp
        prefs.weatherCachedCondition = data.condition
        prefs.weatherCacheTimestamp = data.timestamp
        return Result.success()
    }
}
