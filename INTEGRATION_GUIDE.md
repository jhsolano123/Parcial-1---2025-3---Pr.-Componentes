# Guía de Integración - Módulo de Datos F1 Pit Stop

## Para las Personas 2 y 3 del Equipo

Esta guía explica cómo integrar el módulo de datos (Persona 1) con las interfaces de usuario que desarrollarán las Personas 2 y 3.

## Configuración Inicial

### 1. Obtener el Repository

En cualquier Activity, Fragment o ViewModel:

```kotlin
class MainActivity : ComponentActivity() {
    private val repository by lazy {
        (application as PitStopApplication).repository
    }
}

// En ViewModel
class PitStopViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as PitStopApplication).repository
}
```

### 2. Dependencias Necesarias

Asegúrense de que estas dependencias estén en su `build.gradle`:

```gradle
// ViewModel
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0'
implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0'

// Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
```

## Operaciones Principales

### Guardar Pit Stop

```kotlin
// En ViewModel
class PitStopFormViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as PitStopApplication).repository
    
    suspend fun savePitStop(
        piloto: String,
        escuderia: Escuderia,
        tiempoTotal: Double,
        cambioNeumaticos: TipoNeumatico,
        numeroNeumaticosCambiados: Int,
        estado: EstadoPitStop,
        motivoFallo: String?,
        mecanicoPrincipal: String
    ): Result<Long> {
        return try {
            val pitStop = PitStop(
                piloto = piloto,
                escuderia = escuderia,
                tiempoTotal = tiempoTotal,
                cambioNeumaticos = cambioNeumaticos,
                numeroNeumaticosCambiados = numeroNeumaticosCambiados,
                estado = estado,
                motivoFallo = motivoFallo,
                mecanicoPrincipal = mecanicoPrincipal
            )
            
            val id = repository.insertPitStop(pitStop)
            Result.success(id)
        } catch (e: PitStopException.ValidationException) {
            Result.failure(e)
        } catch (e: PitStopException.DatabaseException) {
            Result.failure(e)
        }
    }
}
```

### Mostrar Lista de Pit Stops

```kotlin
// En ViewModel para la lista
class PitStopListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as PitStopApplication).repository
    
    // Flow que se actualiza automáticamente
    val pitStops: Flow<List<PitStop>> = repository.getAllPitStops()
    
    // Para búsqueda
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    val filteredPitStops: Flow<List<PitStop>> = searchQuery
        .debounce(300) // Esperar 300ms después de que el usuario deje de escribir
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getAllPitStops()
            } else {
                repository.searchPitStops(query)
            }
        }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    suspend fun deletePitStop(pitStop: PitStop) {
        try {
            repository.deletePitStop(pitStop)
        } catch (e: PitStopException) {
            // Manejar error
        }
    }
}
```

### Mostrar Estadísticas (Resumen)

```kotlin
// En ViewModel para el resumen
class PitStopSummaryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = (application as PitStopApplication).repository
    
    private val _statistics = MutableStateFlow(PitStopStatistics.empty())
    val statistics: StateFlow<PitStopStatistics> = _statistics.asStateFlow()
    
    init {
        loadStatistics()
    }
    
    private fun loadStatistics() {
        viewModelScope.launch {
            try {
                val stats = repository.getStatistics()
                _statistics.value = stats
            } catch (e: PitStopException) {
                // Manejar error
            }
        }
    }
    
    fun refreshStatistics() {
        loadStatistics()
    }
}
```

## Uso en Compose

### Lista de Pit Stops

```kotlin
@Composable
fun PitStopListScreen(viewModel: PitStopListViewModel = viewModel()) {
    val pitStops by viewModel.filteredPitStops.collectAsState(initial = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsState()
    
    Column {
        // Campo de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::updateSearchQuery,
            label = { Text("Buscar") },
            modifier = Modifier.fillMaxWidth()
        )
        
        // Lista de pit stops
        LazyColumn {
            items(pitStops) { pitStop ->
                PitStopItem(
                    pitStop = pitStop,
                    onDelete = { 
                        // Confirmar eliminación
                        viewModel.viewModelScope.launch {
                            viewModel.deletePitStop(pitStop)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PitStopItem(
    pitStop: PitStop,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = pitStop.piloto,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = pitStop.escuderia.displayName,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = pitStop.getTiempoFormateado(),
                style = MaterialTheme.typography.bodyLarge
            )
            
            // Botón de eliminar
            Button(onClick = onDelete) {
                Text("Eliminar")
            }
        }
    }
}
```

### Pantalla de Resumen

```kotlin
@Composable
fun PitStopSummaryScreen(viewModel: PitStopSummaryViewModel = viewModel()) {
    val statistics by viewModel.statistics.collectAsState()
    
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Resumen de Pit Stops",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Estadísticas
        StatisticCard(
            title = "Pit stop más rápido",
            value = statistics.getFastestTimeFormatted()
        )
        
        StatisticCard(
            title = "Promedio de tiempos",
            value = statistics.getAverageTimeFormatted()
        )
        
        StatisticCard(
            title = "Total de paradas",
            value = statistics.totalCount.toString()
        )
        
        // Gráfico simple (pueden usar una librería como MPAndroidChart)
        if (statistics.hasData()) {
            SimpleBarChart(
                data = statistics.getTimesForChart(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

@Composable
fun StatisticCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
```

### Formulario de Registro

```kotlin
@Composable
fun PitStopFormScreen(viewModel: PitStopFormViewModel = viewModel()) {
    var piloto by remember { mutableStateOf("") }
    var escuderia by remember { mutableStateOf(Escuderia.MERCEDES) }
    var tiempo by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf(EstadoPitStop.OK) }
    var mecanico by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    Column(modifier = Modifier.padding(16.dp)) {
        // Campos del formulario
        OutlinedTextField(
            value = piloto,
            onValueChange = { piloto = it },
            label = { Text("Piloto") },
            modifier = Modifier.fillMaxWidth()
        )
        
        // Dropdown para escudería
        DropdownMenuField(
            value = escuderia.displayName,
            options = Escuderia.getDisplayNames(),
            onValueChange = { escuderia = Escuderia.fromString(it) },
            label = "Escudería"
        )
        
        OutlinedTextField(
            value = tiempo,
            onValueChange = { tiempo = it },
            label = { Text("Tiempo (segundos)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = mecanico,
            onValueChange = { mecanico = it },
            label = { Text("Mecánico Principal") },
            modifier = Modifier.fillMaxWidth()
        )
        
        // Mostrar error si existe
        errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        // Botón guardar
        Button(
            onClick = {
                viewModel.viewModelScope.launch {
                    val result = viewModel.savePitStop(
                        piloto = piloto,
                        escuderia = escuderia,
                        tiempoTotal = tiempo.toDoubleOrNull() ?: 0.0,
                        cambioNeumaticos = TipoNeumatico.SOFT,
                        numeroNeumaticosCambiados = 4,
                        estado = estado,
                        motivoFallo = null,
                        mecanicoPrincipal = mecanico
                    )
                    
                    result.fold(
                        onSuccess = { 
                            // Navegar de vuelta o mostrar éxito
                        },
                        onFailure = { exception ->
                            errorMessage = exception.message
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
```

## Datos Disponibles

### Enums para Dropdowns

```kotlin
// Para poblar dropdowns
val escuderias = Escuderia.getDisplayNames()
val tiposNeumatico = TipoNeumatico.getDisplayNames()
val estados = EstadoPitStop.getDisplayNames()
```

### Métodos Útiles de PitStop

```kotlin
val pitStop: PitStop = // ...

// Formateo para UI
pitStop.getFechaFormateada() // "19/10/2024 14:30"
pitStop.getTiempoFormateado() // "2.5 s"
pitStop.fueExitoso() // true si estado == OK
```

### Métodos Útiles de PitStopStatistics

```kotlin
val stats: PitStopStatistics = // ...

stats.getFastestTimeFormatted() // "2.1 s" o "N/A"
stats.getAverageTimeFormatted() // "2.82 s" o "N/A"
stats.hasData() // true si hay pit stops
stats.getTimesForChart() // List<Double> para gráficos
```

## Manejo de Errores

```kotlin
try {
    repository.insertPitStop(pitStop)
} catch (e: PitStopException.ValidationException) {
    // Mostrar errores de validación al usuario
    showError(e.message)
} catch (e: PitStopException.DatabaseException) {
    // Error de base de datos
    showError("Error al guardar. Intente nuevamente.")
} catch (e: PitStopException.NotFoundException) {
    // Pit stop no encontrado
    showError("Pit stop no encontrado")
}
```

## Datos de Prueba

Para desarrollo y testing, pueden usar:

```kotlin
// Crear datos de ejemplo
val samplePitStops = PitStopFactory.createSamplePitStops(10)

// Poblar base de datos con datos de prueba
viewModelScope.launch {
    samplePitStops.forEach { pitStop ->
        repository.insertPitStop(pitStop)
    }
}
```

## Navegación Sugerida

```kotlin
// En MainActivity o NavHost
NavHost(navController, startDestination = "summary") {
    composable("summary") { 
        PitStopSummaryScreen(
            onNavigateToList = { navController.navigate("list") },
            onNavigateToForm = { navController.navigate("form") }
        )
    }
    composable("list") { 
        PitStopListScreen(
            onNavigateToForm = { navController.navigate("form") }
        )
    }
    composable("form") { 
        PitStopFormScreen(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
```

## Contacto

Si tienen dudas sobre la integración, pueden consultar:
- El código fuente en `app/src/main/java/com/f1pitstop/app/data/`
- Los tests en `app/src/test/java/` para ver ejemplos de uso
- Este documento de integración

¡Éxito con la implementación de la UI!