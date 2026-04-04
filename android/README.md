# OOTD - 今日穿搭 Android App

An AI-powered outfit recommendation application for Android built with Jetpack Compose and modern Android architecture.

## Project Structure

```
android/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── kotlin/com/ootd/app/
│   │       │   ├── data/
│   │       │   │   ├── dao/                    # Room DAOs
│   │       │   │   ├── database/               # Room Database
│   │       │   │   ├── entity/                 # Database entities
│   │       │   │   ├── network/                # Network layer
│   │       │   │   │   ├── api/
│   │       │   │   │   └── dto/
│   │       │   │   └── repository/             # Repository implementations
│   │       │   ├── di/                         # Dependency Injection (Hilt)
│   │       │   ├── domain/
│   │       │   │   ├── algorithm/              # Recommendation engine
│   │       │   │   ├── model/                  # Domain models
│   │       │   │   └── repository/             # Repository interfaces
│   │       │   ├── ui/
│   │       │   │   ├── screen/                 # Compose screens
│   │       │   │   ├── theme/                  # Material3 theme
│   │       │   │   └── viewmodel/              # ViewModels
│   │       │   └── util/                       # Utility classes
│   │       └── res/                            # Resources
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Tech Stack

- **UI**: Jetpack Compose + Material3
- **Architecture**: MVVM + Repository Pattern
- **Database**: Room (SQLite)
- **Network**: Retrofit + OkHttp + Kotlin Serialization
- **DI**: Hilt
- **Coroutines**: Flow + StateFlow
- **Image Loading**: Coil
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

## Key Features

### 1. Wardrobe Management
- Add clothing items with photos
- Categorize by type (tops, bottoms, shoes, accessories)
- Tag with attributes (color, material, season, occasion, style)
- Search and filter functionality
- Favorite marking

### 2. Smart Recommendations
- Scene-based selection (daily, business, casual, sports, romantic, party, home, travel)
- Weather-aware suggestions
- Color harmony algorithm
- Seasonal considerations
- System-generated or manual outfit creation

### 3. User Profiles
- Personal information management
- Style and color preferences
- Height/weight tracking
- Preference-based recommendations

### 4. Data Models

#### Key Entities
- **User**: User profile with preferences
- **Clothing**: Individual clothing items
- **Outfit**: Complete outfit combinations
- **WeatherCache**: Cached weather data

## Database Schema

### Users Table
- id (PK): UUID
- phone: String
- nickname: String
- gender: String (MALE, FEMALE, OTHER)
- height, weight: Int
- stylePreferences: String (comma-separated)
- colorPreferences: String (comma-separated)

### Clothings Table
- id (PK): UUID
- userId (FK): String
- name: String
- category: String
- subCategory: String
- colors: String (comma-separated)
- material: String
- seasons: String (comma-separated)
- occasions: String (comma-separated)
- styles: String (comma-separated)
- imageUrl, thumbnailUrl: String
- wearCount: Int
- isFavorite: Boolean

### Outfits Table
- id (PK): UUID
- userId (FK): String
- topId, bottomId, shoesId: String (FK to Clothing)
- onePieceId: String (alternative to top+bottom)
- accessoryIds: String (comma-separated)
- occasion, season: String
- weatherTags: String (comma-separated)
- wearCount: Int
- isFavorite: Boolean

## Building & Running

### Prerequisites
- Android Studio (latest)
- JDK 17+
- Android SDK 26+

### Build
```bash
./gradlew build
```

### Run Debug
```bash
./gradlew installDebug
```

### Build Release APK
```bash
./gradlew bundleRelease
```

## Configuration

### Weather API
Edit `WeatherRepositoryImpl.kt`:
```kotlin
companion object {
    private const val API_KEY = "YOUR_WEATHER_API_KEY"
}
```

Use Qweather.com or similar service for weather data.

## Architecture Patterns

### MVVM + Repository
```
UI (Compose) ← ViewModel ← Repository ← Data Layer
                                ├── Local (Room)
                                └── Remote (Retrofit)
```

### Dependency Injection (Hilt)
- DatabaseModule: Database & DAOs
- NetworkModule: Retrofit & HTTP
- RepositoryModule: Repository implementations

### State Management
- StateFlow for UI state
- Coroutines for async operations
- Flow for continuous data streams

## Recommendation Algorithm

The `RecommendationEngine` generates outfit suggestions based on:

1. **Filtering Phase**
   - Occasion matching
   - Season compatibility
   - Weather appropriateness

2. **Scoring Phase**
   - Color harmony (complementary, analogous, monochromatic)
   - User preferences
   - Recency (avoid recent recommendations)

3. **Color Harmony Rules**
   - Complementary: 1.0x weight
   - Analogous: 0.8x weight
   - Monochromatic: 0.7x weight
   - Triadic: 0.9x weight
   - Neutral base: 0.6x weight

## UI Screens

### HomeScreen
- Weather display with refresh
- Occasion selector
- Outfit recommendations
- Loading and error states

### WardrobeScreen
- Category filter (ALL, TOP, BOTTOM, etc.)
- Clothing grid view
- Favorite toggle
- Delete functionality

### AddClothingScreen
- Name, category, subcategory input
- Multi-select colors, seasons, occasions, styles
- Material and brand selection
- Price and notes
- Image upload

### UserScreen
- User creation form (initial)
- Profile display with edit
- Personal info and preferences
- Style and color preference chips

## File Descriptions

### Data Layer
- `entity/*`: Room entity classes
- `dao/*`: Data Access Objects
- `database/OotdDatabase.kt`: Room database definition
- `network/api/WeatherApi.kt`: Retrofit API interface
- `repository/*Impl.kt`: Repository implementations

### Domain Layer
- `model/`: Domain models and enums
- `algorithm/RecommendationEngine.kt`: Outfit generation logic
- `repository/`: Repository interfaces

### UI Layer
- `screen/MainActivity.kt`: Entry point
- `screen/HomeScreen.kt`: Home/recommendations
- `screen/WardrobeScreen.kt`: Wardrobe management
- `screen/AddClothingScreen.kt`: Add new clothing
- `screen/UserScreen.kt`: User profile
- `viewmodel/*ViewModel.kt`: State management
- `theme/*`: Material3 theme setup

### Utilities
- `util/DateUtils.kt`: Date formatting
- `util/ColorUtils.kt`: Color harmony helpers
- `util/ImageUtils.kt`: Image caching

## Testing

Unit tests for core logic:
```bash
./gradlew testDebugUnitTest
```

Integration tests:
```bash
./gradlew connectedDebugAndroidTest
```

## Future Enhancements

1. **Phase 2**
   - AI image recognition (TensorFlow Lite)
   - Social sharing
   - Advanced analytics

2. **Phase 3**
   - Cloud sync
   - Community features
   - Shopping integration

3. **Phase 4**
   - Wear OS companion app
   - Home automation integration
   - Fashion brand partnerships

## License

Proprietary - OOTD Team 2026

## References

- [Jetpack Compose Docs](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Android Architecture Guide](https://developer.android.com/guide/architecture)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)
