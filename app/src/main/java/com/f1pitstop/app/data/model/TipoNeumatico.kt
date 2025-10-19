package com.f1pitstop.app.data.model

/**
 * Enum que representa los diferentes tipos de neumáticos disponibles en F1
 */
enum class TipoNeumatico(val displayName: String) {
    SOFT("Soft"),
    MEDIUM("Medium"),
    HARD("Hard");
    
    companion object {
        /**
         * Convierte un string a TipoNeumatico
         * @param value String a convertir
         * @return TipoNeumatico correspondiente o SOFT por defecto
         */
        fun fromString(value: String): TipoNeumatico {
            return values().find { it.displayName.equals(value, ignoreCase = true) } ?: SOFT
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