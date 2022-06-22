package com.connect.door2door.data.remote.repository


import com.connect.door2door.data.remote.ConnectionState
import com.connect.door2door.domain.model.RiderData
import com.connect.door2door.domain.repository.D2DSocketService
import com.connect.door2door.util.Constants
import com.connect.door2door.util.Constants.EMPTY
import com.connect.door2door.util.extension.parseData
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive

/**
 * This class is for implementation of service interface
 */
class D2DSocketServiceImpl(
    private val client: HttpClient
) : D2DSocketService {

    private var socketSession: WebSocketSession? = null

    override suspend fun openSession(): ConnectionState {
        return try {
            socketSession = client.webSocketSession {
                url(Constants.BASE_URL)
            }
            if (socketSession?.isActive == true) {
                ConnectionState.Connected(Constants.CONNECTION)
            } else {
                ConnectionState.CannotConnect(Constants.CONNECTION_NOT_ESTABLISHED)
            }
        } catch (e: Exception) {
            ConnectionState.Error(Constants.UNKNOWN_ERROR)
        }
    }


    override fun refreshData(): Flow<RiderData> {
        return try {
            socketSession?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: EMPTY
                    parseData(json)
                } ?: flow {}
        } catch (e: Exception) {
            flow {}
        }
    }

    override suspend fun closeSession() {
        socketSession?.close()
    }
}
