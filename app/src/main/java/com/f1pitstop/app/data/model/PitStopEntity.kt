package com.f1pitstop.app.data.model

/**
 * Alias que representa la entidad de Room utilizada para persistir los Pit Stops.
 *
 * Actualmente la entidad coincide con el modelo de dominio [PitStop], por lo que se
 * reutiliza directamente. Este typealias permite desacoplar la capa de datos de la UI
 * y seguir la convención solicitada en los requerimientos.
 */
typealias PitStopEntity = PitStop