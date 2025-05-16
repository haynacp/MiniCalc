package com.haynacardoso.minicalc.di

import com.haynacardoso.minicalc.data.repository.HistoryRepository
import com.haynacardoso.minicalc.domain.HistoryDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHistoryRepository(): HistoryDataSource {
        return HistoryRepository()
    }
}