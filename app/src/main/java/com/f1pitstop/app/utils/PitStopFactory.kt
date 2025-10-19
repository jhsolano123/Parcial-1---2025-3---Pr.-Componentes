package com.f1pitstop.app.utils

import com.f1pitstop.app.data.model.*
import kotlin.random.Random

/**
 * Factory para crear datos de prueba de PitStop
 */
object PitStopFactory {
    
    // Datos de ejemplo para generar pit stops realistas
    private val pilotos = listOf(
        "Lewis Hamilton", "Max Verstappen", "Charles Leclerc", "George Russell",
        "Carlos Sainz", "Lando Norris", "Oscar Piastri", "Fernando Alonso",
        "Lance Stroll", "Esteban Ocon", "Pierre Gasly", "Yuki Tsunoda",
        "Alex Albon", "Logan Sargeant", "Valtteri Bottas", "Zhou Guanyu",
        "Kevin Magnussen", "Nico Hulkenberg", "Daniel Ricciardo", "Nyck de Vries"
    )
    
    private val mecanicos = listOf(
        "John Smith", "Mike Johnson", "Carlos Rodriguez", "Antonio Silva",
        "James Wilson", "Robert Brown", "David Garcia", "Michael Davis",
        "Chris Martinez", "Paul Anderson", "Mark Thompson", "Steve White",
        "Tony Lopez", "Frank Miller", "Joe Taylor", "Sam Jackson"
    )
    
    private val motivosFallo = listOf(
        "Problema con pistola neumática",
        "Neumático mal colocado",
        "Fallo en el gato hidráulico",
        "Comunicación deficiente",
        "Problema mecánico del auto",
        "Neumático defectuoso",
        "Error humano",
        "Problema con combustible"
    )
    
    /**
     * Crea un PitStop con datos aleatorios realistas
     * @return PitStop con datos de ejemplo
     */
    fun createRandomPitStop(): PitStop {
        val estado = if (Random.nextFloat() < 0.8f) EstadoPitStop.OK else EstadoPitStop.FALLIDO
        val escuderia = Escuderia.values().random()
        val piloto = pilotos.random()
        
        return PitStop(
            piloto = piloto,
            escuderia = escuderia,
            tiempoTotal = generateRealisticTime(estado),
            cambioNeumaticos = TipoNeumatico.values().random(),
            numeroNeumaticosCambiados = Random.nextInt(1, 5),
            estado = estado,
            motivoFallo = if (estado == EstadoPitStop.FALLIDO) motivosFallo.random() else null,
            mecanicoPrincipal = mecanicos.random(),
            fechaHora = generateRandomTimestamp()
        )
    }
    
    /**
     * Crea un PitStop específico para testing
     * @param piloto Nombre del piloto
     * @param escuderia Escudería
     * @param tiempo Tiempo en segundos
     * @param estado Estado del pit stop
     * @return PitStop con datos específicos
     */
    fun createSpecificPitStop(
        piloto: String = "Test Pilot",
        escuderia: Escuderia = Escuderia.MERCEDES,
        tiempo: Double = 2.5,
        estado: EstadoPitStop = EstadoPitStop.OK
    ): PitStop {
        return PitStop(
            piloto = piloto,
            escuderia = escuderia,
            tiempoTotal = tiempo,
            cambioNeumaticos = TipoNeumatico.SOFT,
            numeroNeumaticosCambiados = 4,
            estado = estado,
            motivoFallo = if (estado == EstadoPitStop.FALLIDO) "Test failure" else null,
            mecanicoPrincipal = "Test Mechanic",
            fechaHora = System.currentTimeMillis()
        )
    }
    
    /**
     * Crea una lista de pit stops de ejemplo
     * @param count Número de pit stops a crear
     * @return Lista de pit stops
     */
    fun createSamplePitStops(count: Int = 10): List<PitStop> {
        return (1..count).map { createRandomPitStop() }
    }
    
    /**
     * Crea pit stops con tiempos específicos para testing de estadísticas
     * @return Lista de pit stops con tiempos conocidos
     */
    fun createPitStopsForStatistics(): List<PitStop> {
        return listOf(
            createSpecificPitStop("Lewis Hamilton", Escuderia.MERCEDES, 2.1, EstadoPitStop.OK),
            createSpecificPitStop("Max Verstappen", Escuderia.RED_BULL, 2.3, EstadoPitStop.OK),
            createSpecificPitStop("Charles Leclerc", Escuderia.FERRARI, 2.8, EstadoPitStop.OK),
            createSpecificPitStop("George Russell", Escuderia.MERCEDES, 3.2, EstadoPitStop.FALLIDO),
            createSpecificPitStop("Carlos Sainz", Escuderia.FERRARI, 2.5, EstadoPitStop.OK)
        )
    }
    
    /**
     * Crea un PitStop inválido para testing de validaciones
     * @return PitStop con datos inválidos
     */
    fun createInvalidPitStop(): PitStop {
        return PitStop(
            piloto = "", // Inválido: vacío
            escuderia = Escuderia.MERCEDES,
            tiempoTotal = -1.0, // Inválido: negativo
            cambioNeumaticos = TipoNeumatico.SOFT,
            numeroNeumaticosCambiados = 10, // Inválido: más de 4
            estado = EstadoPitStop.FALLIDO,
            motivoFallo = null, // Inválido: fallo sin motivo
            mecanicoPrincipal = "", // Inválido: vacío
            fechaHora = System.currentTimeMillis() + 86400000 // Inválido: futuro
        )
    }
    
    /**
     * Genera un tiempo realista basado en el estado
     * @param estado Estado del pit stop
     * @return Tiempo en segundos
     */
    private fun generateRealisticTime(estado: EstadoPitStop): Double {
        return when (estado) {
            EstadoPitStop.OK -> Random.nextDouble(1.8, 4.0) // Pit stops exitosos: 1.8-4.0s
            EstadoPitStop.FALLIDO -> Random.nextDouble(3.0, 15.0) // Pit stops fallidos: 3.0-15.0s
        }
    }
    
    /**
     * Genera un timestamp aleatorio de los últimos 30 días
     * @return Timestamp en milisegundos
     */
    private fun generateRandomTimestamp(): Long {
        val now = System.currentTimeMillis()
        val thirtyDaysAgo = now - (30 * 24 * 60 * 60 * 1000L)
        return Random.nextLong(thirtyDaysAgo, now)
    }
}