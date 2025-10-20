# Design Document - F1 PitStop UI & Features

## Overview

Este diseño define la arquitectura y implementación de la interfaz de usuario para la aplicación F1 PitStop. Utilizaremos Jetpack Compose con arquitectura MVVM, aprovechando el backend ya implementado (Room, Repository, DAO) para crear una experiencia de usuario fluida y moderna.

## Architecture

### MVVM Pattern
```
View (Compose) ↔ ViewModel ↔ Repository ↔ DAO ↔ Database
```

**Componentes principales:**
- **Views**: Composables de Jetpack Compose
- **ViewModels**: Manejo de estado y lógica de presentación
- **Repository**: Ya implementado (PitStopRepository)
- **Database**: Ya implementado (Room + PitStopDao)

### Navigation Structure
```
MainActivity
├── PitStopNavigation (NavHost)
    ├── PitStopListScreen (inicio)
    ├── AddEditPitStopScreen
    ├── PitStopDetailScreen
    └── SummaryScreen
```

## Components and Interfaces

### 1. ViewModels

#### PitStopListViewModel
```kotlin
class PitStopListViewModel(repository: PitStopRepository) : ViewModel() {
    val pitStops: StateFlow<List<PitStop>>
    val searchQuery: StateFlow<String>
    val filteredPitStops: StateFlow<List<PitStop>>
    val isLoading: StateFlow<Boolean>
    
    fun updateSearchQuery(query: String)
    fun deletePitStop(pitStop: PitStop)
    fun refreshPitStops()
}
```

#### AddEditPitStopViewModel
```kotlin
class AddEditPitStopViewModel(repository: PitStopRepository) : ViewModel() {
    val uiState: StateFlow<AddEditUiState>
    
    fun updateDriver(driver: String)
    fun updateTeam(team: String)
    fun updateTime(time: Double)
    fun savePitStop()
    fun loadPitStop(id: Long)
}
```

#### SummaryViewModel
```kotlin
class SummaryViewModel(repository: PitStopRepository) : ViewModel() {
    val summaryStats: StateFlow<SummaryStats>
    val chartData: StateFlow<List<ChartEntry>>
    val isLoading: StateFlow<Boolean>
    
    fun refreshStats()
}
```

### 2. UI Components

#### Core Screens
- **PitStopListScreen**: Lista principal con búsqueda
- **AddEditPitStopScreen**: Formulario para crear/editar
- **PitStopDetailScreen**: Vista detallada de un pit stop
- **SummaryScreen**: Estadísticas y gráficos

#### Reusable Components
- **PitStopCard**: Tarjeta individual para mostrar pit stop
- **SearchBar**: Barra de búsqueda con filtrado
- **StatCard**: Tarjeta para mostrar estadísticas
- **BarChart**: Gráfico de barras personalizado
- **LoadingIndicator**: Indicador de carga
- **ErrorMessage**: Componente para mostrar errores

### 3. Data Models (UI State)

```kotlin
data class AddEditUiState(
    val driver: String = "",
    val team: String = "",
    val time: String = "",
    val date: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isValid: Boolean = false
)

data class SummaryStats(
    val fastestPitStop: PitStop? = null,
    val averageTime: Double = 0.0,
    val totalPitStops: Int = 0,
    val teamStats: Map<String, Int> = emptyMap()
)

data class ChartEntry(
    val label: String,
    val value: Double,
    val color: Color
)
```

## Data Models

Los modelos de datos principales ya están implementados:
- **PitStop**: Entidad principal (ya existe)
- **PitStopDao**: Operaciones de base de datos (ya existe)
- **PitStopRepository**: Capa de abstracción (ya existe)

## Error Handling

### Error Types
1. **Validation Errors**: Campos vacíos, formatos incorrectos
2. **Database Errors**: Fallos en operaciones CRUD
3. **Network Errors**: Si se implementa sincronización futura

### Error Handling Strategy
```kotlin
sealed class UiState<T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
```

### User Feedback
- **SnackBar**: Para mensajes temporales (éxito/error)
- **Dialog**: Para confirmaciones (eliminar pit stop)
- **Inline Errors**: En formularios para validación
- **Empty States**: Cuando no hay datos

## Testing Strategy

### Unit Tests (Ya implementados)
- ✅ PitStopDao tests
- ✅ PitStopRepository tests
- ✅ Integration tests
- ✅ Validation utils tests

### Nuevos Tests Requeridos
- **ViewModel Tests**: Lógica de presentación
- **Compose UI Tests**: Interacciones de usuario
- **Navigation Tests**: Flujo entre pantallas

### Test Structure
```
app/src/test/java/
├── viewmodel/
│   ├── PitStopListViewModelTest.kt
│   ├── AddEditPitStopViewModelTest.kt
│   └── SummaryViewModelTest.kt
└── ui/
    ├── PitStopListScreenTest.kt
    ├── AddEditPitStopScreenTest.kt
    └── SummaryScreenTest.kt
```

## UI/UX Design Principles

### Material Design 3
- Usar Material 3 components
- Seguir guidelines de accesibilidad
- Implementar tema claro/oscuro

### Color Scheme (F1 Theme)
```kotlin
val F1Red = Color(0xFFE10600)
val F1Black = Color(0xFF15151E)
val F1White = Color(0xFFFFFFFF)
val F1Gray = Color(0xFF38383F)
```

### Typography
- Headlines: Para títulos de pantalla
- Body: Para contenido general
- Caption: Para metadatos (fechas, tiempos)

### Layout Patterns
- **List + FAB**: Lista principal con botón flotante
- **Form Layout**: Formularios con validación visual
- **Card Layout**: Para mostrar información agrupada
- **Grid Layout**: Para estadísticas en resumen

## Performance Considerations

### State Management
- Usar `StateFlow` para reactive UI
- Implementar `collectAsStateWithLifecycle`
- Manejar configuración changes apropiadamente

### Database Operations
- Todas las operaciones DB en background threads
- Usar Flow para observar cambios en tiempo real
- Implementar paginación si la lista crece mucho

### Memory Management
- ViewModels con lifecycle awareness
- Proper cleanup en Composables
- Evitar memory leaks en coroutines

## Implementation Phases

### Phase 1: Core Navigation & List
- Configurar Navigation Compose
- Implementar PitStopListScreen básico
- Crear PitStopListViewModel

### Phase 2: CRUD Operations
- Implementar AddEditPitStopScreen
- Crear formularios con validación
- Implementar eliminación con confirmación

### Phase 3: Search & Filtering
- Agregar funcionalidad de búsqueda
- Implementar filtros en tiempo real
- Optimizar rendimiento de búsqueda

### Phase 4: Summary & Statistics
- Crear SummaryScreen
- Implementar cálculos estadísticos
- Crear gráfico de barras personalizado

### Phase 5: Polish & Testing
- Mejorar UI/UX
- Agregar animaciones
- Completar testing suite
- Documentación final