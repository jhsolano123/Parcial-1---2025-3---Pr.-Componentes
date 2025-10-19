package com.f1pitstop.app.data.exception

/**
 * Excepciones personalizadas para la aplicación de Pit Stops
 */
sealed class PitStopException(message: String) : Exception(message) {
    
    /**
     * Excepción para errores de validación de datos
     */
    class ValidationException(message: String) : PitStopException(message)
    
    /**
     * Excepción para errores de base de datos
     */
    class DatabaseException(message: String) : PitStopException(message)
    
    /**
     * Excepción cuando no se encuentra un pit stop
     */
    class NotFoundException(message: String) : PitStopException(message)
    
    /**
     * Excepción para operaciones no permitidas
     */
    class OperationNotAllowedException(message: String) : PitStopException(message)
}