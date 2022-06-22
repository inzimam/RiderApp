package com.connect.door2door.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connect.door2door.data.remote.ConnectionState
import com.connect.door2door.domain.model.RiderData
import com.connect.door2door.domain.repository.D2DSocketService
import com.connect.door2door.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RiderHomeViewModel @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val d2dSocketService: D2DSocketService
) : ViewModel() {

    private var _riderDataState = MutableStateFlow(RiderData())
    val riderDataState: StateFlow<RiderData> = _riderDataState

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    /**
     * This method is used to notify repository for opening web socket connection
     */
    fun connectToSocket() {
        viewModelScope.launch(dispatchers.main) {
            when (val result = d2dSocketService.openSession()) {
                is ConnectionState.Connected -> {
                    _toastEvent.emit(result.message.toString())
                    refreshData()
                }
                is ConnectionState.CannotConnect -> _toastEvent.emit(result.message.toString())
                else -> _toastEvent.emit(result.message.toString())
            }
        }
    }

    /**
     * This method is used for getting refreshed data from repository and notify to UI
     */

    fun refreshData() {
        d2dSocketService.refreshData().flowOn(dispatchers.main)
            .onEach {
                _riderDataState.value = it
            }.launchIn(viewModelScope)
    }

    /**
     * This method is used to notify repository to disconnect web socket
     */
    fun disconnect() {
        viewModelScope.launch(dispatchers.main) { d2dSocketService.closeSession() }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}