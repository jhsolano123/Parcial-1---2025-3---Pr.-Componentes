package com.f1pitstop.app.data.model

/**
 * Enum que representa las escuderías de Fórmula 1
 */
enum class Escuderia(val displayName: String) {
    MERCEDES("Mercedes"),
    RED_BULL("Red Bull"),
    FERRARI("Ferrari"),
    MCLAREN("McLaren"),
    ALPINE("Alpine"),
    ASTON_MARTIN("Aston Martin"),
    WILLIAMS("Williams"),
    ALFA_ROMEO("Alfa Romeo"),
    HAAS("Haas"),
    ALPHATAURI("AlphaTauri");
    
    companion object {
        /**
         * Convierte un string a Escuderia
         * @param value String a convertir
         * @return Escuderia correspondiente o MERCEDES por defecto
         */
        fun fromString(value: String): Escuderia {
            return values().find { it.displayName.equals(value, ignoreCase = true) } ?: MERCEDES
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