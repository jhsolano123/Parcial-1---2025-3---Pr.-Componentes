package com.f1pitstop.app.data.model

/**
 * Enum que representa el estado de un pit stop
 */
enum class EstadoPitStop(val displayName: String) {
    OK("OK"),
    FALLIDO("Fallido");
    
    companion object {
        /**
         * Convierte un string a EstadoPitStop
         * @param value String a convertir
         * @return EstadoPitStop correspondiente o OK por defecto
         */
        fun fromString(value: String): EstadoPitStop {
            return values().find { it.displayName.equals(value, ignoreCase = true) } ?: OK
        }
        
        /**
         * Obtiene todos los nombres para mostrar en UI
         * @return Lista de strings con los nombres
         */
        fun getDisplayNames(): List<String> {
            return values().map { it.displayName }
        }
    }
}