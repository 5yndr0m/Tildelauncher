package app.tildelauncher.helper

import app.tildelauncher.data.AppModel

interface AppFilterHelper {
    fun onAppFiltered(items:List<AppModel>)
}