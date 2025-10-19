package com.f1pitstop.app.data.model

/**
 * Data class que contiene las estadísticas de pit stops
 */
data class PitStopStatistics(
    val fastestTime: Double?,
    val averageTime: Double?,
    val totalCount: Int,
    val lastFivePitStops: List<PitStop>
) {
    /**
     * Obtiene el tiempo más rápido formateado
     * @return String con el tiempo formateado o "N/A" si no hay datos
     */
    fun getFastestTimeFormatted(): String {
        return fastestTime?.let { String.format("%.1f s", it) } ?: "N/A"
    }
    
    /**
     * Obtiene el promedio formateado
     * @return String con el promedio formateado o "N/A" si no hay datos
     */
    fun getAverageTimeFormatted(): String {
        return averageTime?.let { String.format("%.2f s", it) } ?: "N/A"
    }
    
    /**
     * Verifica si hay datos estadísticos disponibles
     * @return true si hay al menos un pit stop registrado
     */
    fun hasData(): Boolean {
        return totalCount > 0
    }
    
    /**
     * Obtiene los tiempos de los últimos pit stops para gráficos
     * @return Lista de tiempos en orden cronológico (más antiguo primero)
     */
    fun getTimesForChart(): List<Double> {
        return lastFivePitStops.reversed().map { it.tiempoTotal }
    }
    
    companion object {
        /**
         * Crea estadísticas vacías
         * @return PitStopStatistics sin datos
         */
        fun empty(): PitStopStatistics {
            return PitStopStatistics(
                fastestTime = null,
                averageTime = null,
                totalCount = 0,
                lastFivePitStops = emptyList()
            )
        }
    }
}