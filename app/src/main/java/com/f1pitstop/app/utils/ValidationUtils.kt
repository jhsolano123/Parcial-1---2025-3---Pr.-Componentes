package com.f1pitstop.app.utils

import com.f1pitstop.app.data.model.ValidationResult
import java.util.regex.Pattern

/**
 * Utilidades para validación de datos
 */
object ValidationUtils {
    
    // Patrones de validación
    private val NAME_PATTERN = Pattern.compile("^[a-zA-ZÀ-ÿ\\s]{2,50}$")
    private val ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9À-ÿ\\s.,!?-]{1,200}$")
    
    /**
     * Valida que un string no esté vacío
     * @param value Valor a validar
     * @param fieldName Nombre del campo para el mensaje de error
     * @return ValidationResult
     */
    fun validateNotEmpty(value: String?, fieldName: String): ValidationResult {
        return if (value.isNullOrBlank()) {
            ValidationResult.invalid("$fieldName no puede estar vacío")
        } else {
            ValidationResult.valid()
        }
    }
    
    /**
     * Valida la longitud de un string
     * @param value Valor a validar
     * @param fieldName Nombre del campo
     * @param minLength Longitud mínima
     * @param maxLength Longitud máxima
     * @return ValidationResult
     */
    fun validateLength(
        value: String?, 
        fieldName: String, 
        minLength: Int = 0, 
        maxLength: Int = Int.MAX_VALUE
    ): ValidationResult {
        if (value == null) {
            return ValidationResult.invalid("$fieldName no puede ser nulo")
        }
        
        return when {
            value.length < minLength -> ValidationResult.invalid("$fieldName debe tener al menos $minLength caracteres")
            value.length > maxLength -> ValidationResult.invalid("$fieldName no puede tener más de $maxLength caracteres")
            else -> ValidationResult.valid()
        }
    }
    
    /**
     * Valida que un nombre tenga formato válido
     * @param name Nombre a validar
     * @param fieldName Nombre del campo
     * @return ValidationResult
     */
    fun validateName(name: String?, fieldName: String): ValidationResult {
        if (name.isNullOrBlank()) {
            return ValidationResult.invalid("$fieldName no puede estar vacío")
        }
        
        return if (NAME_PATTERN.matcher(name).matches()) {
            ValidationResult.valid()
        } else {
            ValidationResult.invalid("$fieldName contiene caracteres no válidos")
        }
    }
    
    /**
     * Valida un rango numérico
     * @param value Valor a validar
     * @param fieldName Nombre del campo
     * @param min Valor mínimo (inclusive)
     * @param max Valor máximo (inclusive)
     * @return ValidationResult
     */
    fun validateRange(value: Double, fieldName: String, min: Double, max: Double): ValidationResult {
        return when {
            value < min -> ValidationResult.invalid("$fieldName debe ser mayor o igual a $min")
            value > max -> ValidationResult.invalid("$fieldName debe ser menor o igual a $max")
            else -> ValidationResult.valid()
        }
    }
    
    /**
     * Valida un rango numérico entero
     * @param value Valor a validar
     * @param fieldName Nombre del campo
     * @param min Valor mínimo (inclusive)
     * @param max Valor máximo (inclusive)
     * @return ValidationResult
     */
    fun validateIntRange(value: Int, fieldName: String, min: Int, max: Int): ValidationResult {
        return when {
            value < min -> ValidationResult.invalid("$fieldName debe ser mayor o igual a $min")
            value > max -> ValidationResult.invalid("$fieldName debe ser menor o igual a $max")
            else -> ValidationResult.valid()
        }
    }
    
    /**
     * Valida que un timestamp no sea futuro
     * @param timestamp Timestamp a validar
     * @param fieldName Nombre del campo
     * @return ValidationResult
     */
    fun validateNotFuture(timestamp: Long, fieldName: String): ValidationResult {
        return if (timestamp > System.currentTimeMillis()) {
            ValidationResult.invalid("$fieldName no puede ser una fecha futura")
        } else {
            ValidationResult.valid()
        }
    }
    
    /**
     * Valida texto alfanumérico con algunos símbolos permitidos
     * @param text Texto a validar
     * @param fieldName Nombre del campo
     * @return ValidationResult
     */
    fun validateAlphanumericText(text: String?, fieldName: String): ValidationResult {
        if (text.isNullOrBlank()) {
            return ValidationResult.valid() // Permitir texto vacío
        }
        
        return if (ALPHANUMERIC_PATTERN.matcher(text).matches()) {
            ValidationResult.valid()
        } else {
            ValidationResult.invalid("$fieldName contiene caracteres no permitidos")
        }
    }
    
    /**
     * Combina múltiples resultados de validación
     * @param validations Lista de ValidationResult
     * @return ValidationResult combinado
     */
    fun combineValidations(vararg validations: ValidationResult): ValidationResult {
        val allErrors = validations.flatMap { it.errors }
        return if (allErrors.isEmpty()) {
            ValidationResult.valid()
        } else {
            ValidationResult.invalid(allErrors)
        }
    }
}