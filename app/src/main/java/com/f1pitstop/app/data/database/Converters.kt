package com.f1pitstop.app.data.database

import androidx.room.TypeConverter
import com.f1pitstop.app.data.model.EstadoPitStop
import com.f1pitstop.app.data.model.Escuderia
import com.f1pitstop.app.data.model.TipoNeumatico

/**
 * Converters para Room Database
 * Convierte tipos personalizados a tipos que Room puede manejar
 */
class Converters {
    
    @TypeConverter
    fun fromTipoNeumatico(tipo: TipoNeumatico): String {
        return tipo.displayName
    }
    
    @TypeConverter
    fun toTipoNeumatico(tipoString: String): TipoNeumatico {
        return TipoNeumatico.fromString(tipoString)
    }
    
    @TypeConverter
    fun fromEstadoPitStop(estado: EstadoPitStop): String {
        return estado.displayName
    }
    
    @TypeConverter
    fun toEstadoPitStop(estadoString: String): EstadoPitStop {
        return EstadoPitStop.fromString(estadoString)
    }
    
    @TypeConverter
    fun fromEscuderia(escuderia: Escuderia): String {
        return escuderia.displayName
    }
    
    @TypeConverter
    fun toEscuderia(escuderiaString: String): Escuderia {
        return Escuderia.fromString(escuderiaString)
    }
}