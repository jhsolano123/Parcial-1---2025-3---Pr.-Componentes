package com.f1pitstop.app

import android.app.Application
import com.f1pitstop.app.data.database.PitStopDatabase
import com.f1pitstop.app.data.repository.PitStopRepository

/**
 * Application class para la aplicación F1 Pit Stop
 * Maneja la inicialización de dependencias globales
 */
class PitStopApplication : Application() {
    
    /**
     * Instancia lazy de la base de datos
     * Se inicializa solo cuando se necesita por primera vez
     */
    val database by lazy { 
        PitStopDatabase.getDatabase(this) 
    }
    
    /**
     * Instancia lazy del repository
     * Depende de la base de datos
     */
    val repository by lazy { 
        PitStopRepository(database.pitStopDao()) 
    }
    
    override fun onCreate() {
        super.onCreate()
        // Aquí se pueden inicializar otras dependencias globales si es necesario
        // Por ejemplo: logging, crash reporting, etc.
    }
}