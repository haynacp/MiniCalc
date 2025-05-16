package com.haynacardoso.minicalc.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.haynacardoso.minicalc.data.local.dataStore
import com.haynacardoso.minicalc.domain.HistoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepository @Inject constructor() : HistoryDataSource {

    private val HISTORY_KEY = stringPreferencesKey("history")

    override suspend fun save(context: Context, newItem: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[HISTORY_KEY] ?: ""
            val updated = if (current.isEmpty()) newItem else "$newItem\n\n$current"
            prefs[HISTORY_KEY] = updated
        }
    }

    override fun get(context: Context): Flow<String> {
        return context.dataStore.data.map { prefs ->
            prefs[HISTORY_KEY] ?: ""
        }
    }

    override suspend fun clear(context: Context) {
        context.dataStore.edit { prefs ->
            prefs[HISTORY_KEY] = ""
        }
    }
}
