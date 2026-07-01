package com.ammar.studentdesk.network.di

import com.ammar.studentdesk.network.source.NetworkDataSource
import com.ammar.studentdesk.network.source.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataModule {

    @Binds
    @Singleton
    fun bindNetworkDataSource(
        networkDataSourceImpl: NetworkDataSourceImpl
    ) : NetworkDataSource
}