package app.tildelauncher.helper

import android.content.ComponentName
import android.content.Context
import android.media.session.MediaSessionManager
import android.provider.Settings
import app.tildelauncher.listener.MediaListenerService

fun isNotificationListenerEnabled(context: Context): Boolean {
    val flat = Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
    return flat?.contains(context.packageName) == true
}

fun getActiveMediaPackage(context: Context): String? {
    return try {
        val component = ComponentName(context, MediaListenerService::class.java)
        val manager = context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
        manager.getActiveSessions(component).firstOrNull()?.packageName
    } catch (e: Exception) {
        null
    }
}
