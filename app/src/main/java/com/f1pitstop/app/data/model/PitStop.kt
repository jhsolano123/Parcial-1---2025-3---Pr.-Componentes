package com.f1pitstop.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

/**
 * Entidad que representa un Pit Stop en la base de datos
 */
@Entity(tableName = "pit_stops")
data class PitStop(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    @ColumnInfo(name = "piloto")
    val piloto: String,
    
    @ColumnInfo(name = "escuderia")
    val escuderia: Escuderia,
    
    @ColumnInfo(name = "tiempo_total")
    val tiempoTotal: Double, // en segundos
    
    @ColumnInfo(name = "cambio_neumaticos")
    val cambioNeumaticos: TipoNeumatico,
    
    @ColumnInfo(name = "numero_neumaticos_cambiados")
    val numeroNeumaticosCambiados: Int,
    
    @ColumnInfo(name = "estado")
    val estado: EstadoPitStop,
    
    @ColumnInfo(name = "motivo_fallo")
    val motivoFallo: String? = null,
    
    @ColumnInfo(name = "mecanico_principal")
    val mecanicoPrincipal: String,
    
    @ColumnInfo(name = "fecha_hora")
    val fechaHora: Long = System.currentTimeMillis() // timestamp
) {
    /**
     * Obtiene la fecha formateada para mostrar en UI
     * @return String con formato dd/MM/yyyy HH:mm
     */
    fun getFechaFormateada(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return formatter.format(Date(fechaHora))
    }
    
    /**
     * Obtiene el tiempo formateado para mostrar en UI
     * @return String con el tiempo en formato "X.X s"
     */
    fun getTiempoFormateado(): String {
        return String.format("%.1f s", tiempoTotal)
    }
    
    /**
     * Verifica si el pit stop fue exitoso
     * @return true si el estado es OK
     */
    fun fueExitoso(): Boolean {
        return estado == EstadoPitStop.OK
    }
    
    companion object {
        /**
         * Crea un PitStop vacío para formularios
         * @return PitStop con valores por defecto
         */
        fun empty(): PitStop {
            return PitStop(
                piloto = "",
                escuderia = Escuderia.MERCEDES,
                tiempoTotal = 0.0,
                cambioNeumaticos = TipoNeumatico.SOFT,
                numeroNeumaticosCambiados = 4,
                estado = EstadoPitStop.OK,
                motivoFallo = null,
                mecanicoPrincipal = ""
            )
        }
    }
}