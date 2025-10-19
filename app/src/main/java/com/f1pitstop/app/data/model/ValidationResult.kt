package com.f1pitstop.app.data.model

/**
 * Data class que representa el resultado de una validación
 */
data class ValidationResult(
    val isValid: Boolean,
    val errors: List<String> = emptyList()
) {
    /**
     * Obtiene el primer error si existe
     * @return Primer mensaje de error o null
     */
    fun getFirstError(): String? {
        return errors.firstOrNull()
    }
    
    /**
     * Obtiene todos los errores como un string concatenado
     * @return String con todos los errores separados por salto de línea
     */
    fun getAllErrorsAsString(): String {
        return errors.joinToString("\n")
    }
    
    /**
     * Verifica si hay errores específicos de un campo
     * @param fieldName Nombre del campo a verificar
     * @return true si hay errores que contengan el nombre del campo
     */
    fun hasErrorForField(fieldName: String): Boolean {
        return errors.any { it.contains(fieldName, ignoreCase = true) }
    }
    
    companion object {
        /**
         * Crea un resultado válido sin errores
         * @return ValidationResult válido
         */
        fun valid(): ValidationResult {
            return ValidationResult(isValid = true, errors = emptyList())
        }
        
        /**
         * Crea un resultado inválido con un error
         * @param error Mensaje de error
         * @return ValidationResult inválido
         */
        fun invalid(error: String): ValidationResult {
            return ValidationResult(isValid = false, errors = listOf(error))
        }
        
        /**
         * Crea un resultado inválido con múltiples errores
         * @param errors Lista de mensajes de error
         * @return ValidationResult inválido
         */
        fun invalid(errors: List<String>): ValidationResult {
            return ValidationResult(isValid = false, errors = errors)
        }
    }
}