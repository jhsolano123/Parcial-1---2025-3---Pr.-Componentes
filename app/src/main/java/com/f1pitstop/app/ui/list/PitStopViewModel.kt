/*package com.f1pitstop.app.ui.pitstoplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.f1pitstop.app.data.exception.PitStopException
import com.f1pitstop.app.data.model.PitStop
import com.f1pitstop.app.data.repository.PitStopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de listado de Pit Stops.
 */
class PitStopViewModel(private val repository: PitStopRepository) : ViewModel() {

    private val _state = MutableStateFlow(PitStopListState())
    val state: StateFlow<PitStopListState> = _state.asStateFlow()

    init {
        observePitStops()
    }

    private fun observePitStops() {
        viewModelScope.launch {
            repository.getAllPitStops()
                .onStart {
                    _state.update { current ->
                        current.copy(isLoading = true, errorMessage = null)
                    }
                }
                .catch { throwable ->
                    val message = (throwable as? PitStopException)?.message ?: throwable.message
                    _state.update { current ->
                        current.copy(isLoading = false, errorMessage = message)
                    }
                }
                .collect { pitStops ->
                    _state.update { current ->
                        current.copy(pitStops = pitStops, isLoading = false)
                    }
                }
        }
    }

    fun onDeleteClick(pitStop: PitStop) {
        _state.update { current ->
            current.copy(pendingDeletion = PitStopToDelete(pitStop))
        }
    }

    fun dismissDeletionDialog() {
        _state.update { current ->
            current.copy(pendingDeletion = null)
        }
    }

    fun deletePitStop(pitStop: PitStop) {
        viewModelScope.launch {
            _state.update { current ->
                current.copy(isLoading = true, pendingDeletion = null)
            }
            try {
                repository.deletePitStop(pitStop)
                _state.update { current ->
                    current.copy(isLoading = false)
                }
            } catch (exception: PitStopException) {
                _state.update { current ->
                    current.copy(isLoading = false, errorMessage = exception.message)
                }
            } catch (exception: Exception) {
                _state.update { current ->
                    current.copy(isLoading = false, errorMessage = exception.message)
                }
            }
        }
    }

    companion object {
        fun provideFactory(repository: PitStopRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(PitStopViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return PitStopViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}

/**
 * Estado de la pantalla de listado de Pit Stops.
 */
data class PitStopListState(
    val pitStops: List<PitStop> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pendingDeletion: PitStopToDelete? = null
)

/**
 * Modelo auxiliar para manejar la eliminación pendiente.
 */
data class PitStopToDelete(val pitStop: PitStop)*/