package com.haynacardoso.minicalc.domain

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface HistoryDataSource {
    suspend fun save(context: Context, newItem: String)
    fun get(context: Context): Flow<String>
    suspend fun clear(context: Context)
}