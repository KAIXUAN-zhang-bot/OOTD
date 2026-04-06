# OpenWeatherMap Integration Design

## API Configuration
- **Base URL**: `https://api.openweathermap.org/data/2.5/`
- **Endpoints**:
  - `weather?lat={lat}&lon={lon}&appid={API_KEY}` (Current weather)
  - `forecast?lat={lat}&lon={lon}&appid={API_KEY}` (Future weather)
- **Key Fields to Extract**:
  - `main.temp` (Celsius - crucial for outfit layers)
  - `weather[0].main` (Rain, Clear, Clouds, Snow - crucial for fabric & shoes)
  - `main.humidity` (Comfort level)

## Data Model (Kotlin)
```kotlin
data class WeatherInfo(
    val temperature: Float,      // Used for Layering matching
    val condition: WeatherType,  // Enum: Clear, Rainy, Snowy, etc.
    val city: String
)

enum class WeatherType {
    SUNNY, RAINY, SNOWY, CLOUDY, WINDY
}
```

## Implementation Strategy
1. **Permission Request**: Request `ACCESS_COARSE_LOCATION` and `ACCESS_FINE_LOCATION`.
2. **Weather Service**: Use Retrofit to fetch data and a `WeatherMapper` to convert API response to `WeatherInfo`.
3. **Caching**: Store weather data in a `LocalDataSource` (Room or DataStore) with a 30-minute expiration to minimize API calls.
