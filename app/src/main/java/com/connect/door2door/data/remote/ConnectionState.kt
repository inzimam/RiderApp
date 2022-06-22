package com.connect.door2door.data.remote

/**
 * sealed class for maintaining the state
 */
sealed class ConnectionState(val message: String? = null) {
    class Connected(message: String) : ConnectionState(message)
    class CannotConnect(message: String) : ConnectionState(message)
    class Error(message: String) : ConnectionState(message)
}