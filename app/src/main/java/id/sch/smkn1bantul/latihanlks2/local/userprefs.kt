package id.sch.smkn1bantul.latihanlks2.local

import android.content.Context

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "story_db")

class UserPrefs(context: Context) {

    private val appContext = context.applicationContext

    val authToken: Flow<String?>
        get() = appContext.dataStore.data.map { prefs ->
            prefs[KEY_AUTH]
        }

    suspend fun saveAuthCredentials(authToken: String) {
        appContext.dataStore.edit { settings ->
            settings[KEY_AUTH] = authToken
        }
    }

    companion object {
        private val KEY_AUTH = stringPreferencesKey("key_auth")
    }

    suspend fun clear() {
        appContext.dataStore.edit { settings ->
            settings.clear()
        }
    }
}