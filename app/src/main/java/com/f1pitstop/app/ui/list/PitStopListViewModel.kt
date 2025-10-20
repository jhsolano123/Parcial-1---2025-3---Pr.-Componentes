// app/src/main/java/com/f1pitstop/app/ui/list/PitStopListViewModel.kt
package com.f1pitstop.app.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.f1pitstop.app.PitStopApplication
import com.f1pitstop.app.data.model.PitStop
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PitStopListViewModel(app: Application): AndroidViewModel(app) {
    private val repo = (app as PitStopApplication).repository

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val items: StateFlow<List<PitStop>> =
        _query.debounce(250)
            .flatMapLatest { q ->
                if (q.isBlank()) repo.getAllPitStops()
                else repo.searchPitStops(q)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onQueryChange(q: String) { _query.value = q }
    fun deleteById(id: Long) = viewModelScope.launch { repo.deletePitStopById(id) }
}
