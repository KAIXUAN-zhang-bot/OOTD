# OOTD App Project Structure

```text
app/src/main/java/com/ootd/app/
├── ui/                     # Jetpack Compose Screens & Components
│   ├── theme/              # Color, Type, Theme definitions
│   ├── home/               # Dashboard with recommendations
│   ├── wardrobe/           # Closet management
│   ├── profile/            # User settings & preferences
│   └── components/         # Reusable UI widgets
├── data/                   # Data Layer
│   ├── local/              # Room DB entities & DAOs
│   ├── remote/             # Retrofit API services
│   ├── repository/         # Repository implementations
│   └── mapper/             # Data to Domain model conversion
├── domain/                 # Domain Layer
│   ├── model/              # Pure Kotlin data classes (Outfit, Item)
│   ├── repository/         # Interfaces (Abstraction)
│   └── usecase/            # Business logic (e.g., GetRecommendationUseCase)
├── di/                     # Dependency Injection (Hilt)
└── util/                   # Common utilities (Permissions, WeatherHelper)
```
