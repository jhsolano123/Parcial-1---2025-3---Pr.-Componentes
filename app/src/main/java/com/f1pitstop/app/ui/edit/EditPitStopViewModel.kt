// app/src/main/java/com/f1pitstop/app/ui/edit/EditPitStopViewModel.kt
package com.f1pitstop.app.ui.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.f1pitstop.app.PitStopApplication
import com.f1pitstop.app.data.model.PitStop

class EditPitStopViewModel(app: Application): AndroidViewModel(app) {
    private val repo = (app as PitStopApplication).repository
    suspend fun load(id: Long) = repo.getPitStop(id)
    suspend fun save(p: PitStop): Long = repo.upsertPitStop(p)
}
