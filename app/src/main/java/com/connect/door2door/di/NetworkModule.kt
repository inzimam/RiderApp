package com.connect.door2door.di

import com.connect.door2door.data.remote.repository.D2DSocketServiceImpl
import com.connect.door2door.domain.repository.D2DSocketService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

/**
 * Module class to provide dependencies for network layer.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets) {
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Provides
    @Singleton
    fun provideD2DSocketService(client: HttpClient): D2DSocketService {
        return D2DSocketServiceImpl(client)
    }
}