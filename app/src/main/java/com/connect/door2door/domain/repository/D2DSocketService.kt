package com.connect.door2door.domain.repository

import com.connect.door2door.data.remote.ConnectionState
import com.connect.door2door.domain.model.RiderData
import kotlinx.coroutines.flow.Flow

/**
 * Service interface for interaction between use case
 */
interface D2DSocketService {
    /**
     * This method is used to open socket connection
     * @return ConnectionState This returns the state of socket connection
     */
    suspend fun openSession(): ConnectionState

    /**
     * This method is used to get updated data from socket
     * @return RiderData This method returns flow of rider data
     */
    fun refreshData(): Flow<RiderData>

    /**
     * This method is used to close socket connection
     */
    suspend fun closeSession()
}