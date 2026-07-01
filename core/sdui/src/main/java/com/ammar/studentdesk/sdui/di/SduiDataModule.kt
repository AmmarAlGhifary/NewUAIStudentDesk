package com.ammar.studentdesk.sdui.di

import com.ammar.studentdesk.sdui.data.repository.SduiRepositoryImpl
import com.ammar.studentdesk.sdui.domain.repository.SduiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SduiDataModule {

    @Binds
    @Singleton
    abstract fun bindSduiRepo(
        impl: SduiRepositoryImpl
    ): SduiRepository
}