package com.f1pitstop.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.f1pitstop.app.data.model.PitStop

/**
 * Base de datos Room para la aplicación F1 Pit Stop
 */
@Database(
    entities = [PitStop::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PitStopDatabase : RoomDatabase() {
    
    /**
     * Obtiene el DAO para operaciones de PitStop
     * @return PitStopDao
     */
    abstract fun pitStopDao(): PitStopDao
    
    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: PitStopDatabase? = null
        
        /**
         * Obtiene la instancia de la base de datos (Singleton)
         * @param context Contexto de la aplicación
         * @return Instancia de PitStopDatabase
         */
        fun getDatabase(context: Context): PitStopDatabase {
            // Si INSTANCE no es null, la retorna, sino crea la base de datos
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PitStopDatabase::class.java,
                    "pitstop_database"
                )
                .fallbackToDestructiveMigration() // En desarrollo, permite recrear la DB
                .build()
                INSTANCE = instance
                // Retorna la instancia
                instance
            }
        }
        
        /**
         * Crea una instancia de base de datos en memoria para testing
         * @param context Contexto de testing
         * @return Instancia de PitStopDatabase en memoria
         */
        fun getInMemoryDatabase(context: Context): PitStopDatabase {
            return Room.inMemoryDatabaseBuilder(
                context.applicationContext,
                PitStopDatabase::class.java
            )
            .allowMainThreadQueries() // Solo para testing
            .build()
        }
        
        /**
         * Cierra la base de datos y limpia la instancia
         * Útil para testing
         */
        fun closeDatabase() {
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}