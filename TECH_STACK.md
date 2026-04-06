# OOTD App Technical Architecture

## Tech Stack (Android)
- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Modern Android UI)
- **Architecture**: MVVM (Model-View-ViewModel) or MVI (Model-View-Intent)
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) (Standard for Android)
- **Networking**: [Retrofit](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)
- **Database**: [Room](https://developer.android.com/training/data-storage/room) (Wardrobe management, offline access)
- **Asynchronous**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) + [Flow](https://kotlinlang.org/docs/flow.html)
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) (Lightweight, Kotlin-first)
- **Weather API**: [OpenWeatherMap](https://openweathermap.org/api) (Current weather + 5-day forecast)
- **Serialization**: [Kotlinx Serialization](https://kotlinlang.org/docs/serialization.html)

## Modules
- `:app` - UI components (Compose screens), ViewModels, Hilt Modules.
- `:domain` - Use cases, Domain models, Repository interfaces.
- `:data` - Repository implementations, Local DB (Room), Remote API (Retrofit), OpenWeather integration.

## Key Technical Goals
1. **Offline First**: Clothes uploaded by the user should be browseable without internet.
2. **AI Recommendation Layer**: An abstraction layer that takes (Weather, Occasion, Mood) and returns a list of sorted wardrobe items.
3. **Smooth Image Experience**: Fast loading and caching of high-res outfit photos.
