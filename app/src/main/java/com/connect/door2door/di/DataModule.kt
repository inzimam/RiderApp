package com.connect.door2door.di

import com.connect.door2door.util.DefaultDispatcher
import com.connect.door2door.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module class to provide dependencies for data layer.
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideDispatchers(): DispatcherProvider {
        return DefaultDispatcher()
    }
}