package com.haynacardoso.minicalc.data.repository

import android.content.Context
import com.haynacardoso.minicalc.domain.HistoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeHistoryRepository : HistoryDataSource {

    private val fakeHistory = mutableListOf<String>()

    override suspend fun save(context: Context, newItem: String) {
        fakeHistory.add(0, newItem)
    }

    override fun get(context: Context): Flow<String> {
        return flowOf(fakeHistory.joinToString("\n\n"))
    }

    override suspend fun clear(context: Context) {
        fakeHistory.clear()
    }
}