# Pokédex App

An educational and engaging Pokédex app built with **Jetpack Compose**, designed to be intuitive and fun for both kids and adults. The app showcases Pokémon data in a colorful, searchable interface with lightweight performance and smooth user experience.

## 🧠 Overview

This project leverages:

- Modern **Jetpack Compose** for UI
- **Kotlin** and **Coroutines** for asynchronous logic
- **DataStore** for persistent state (e.g., "My Pokémon")
- **PokeKotlin** client library for API integration
- **Coil** for image loading
- Test-friendly architecture with abstraction and in-memory caching

## ✨ Features

- 🎲 **Featured Screen**: Discover a random Pokémon on app launch, complete with flavor text and catch button.
  - ✨ The featured Pokémon is preserved across tab switches using `rememberSaveable`.
- 🌟 **Shiny Mode**: Catch shiny Pokémon based on probability and toggle shiny state using persistent storage.
- 📚 **Pokédex**: Browse and search all Pokémon using fuzzy matching.
- ❤️ **My Pokémon**: View your favorite caught Pokémon in a simple 2-column layout.
- 🔍 **Detail View**: Get detailed info for each Pokémon including types and description.
  - 📝 Flavor text is lazily loaded from species data on demand, improving performance and reducing memory usage.

## 📦 Technologies Used

| Category         | Tech                                                                                                 |
| ---------------- | ---------------------------------------------------------------------------------------------------- |
| Language         | Kotlin (JVM target 11)                                                                               |
| UI               | Jetpack Compose (Material3)                                                                          |
| Navigation       | `androidx.navigation.compose`                                                                        |
| Image Loading    | [Coil](https://github.com/coil-kt/coil)                                                              |
| Data Persistence | Jetpack DataStore (Preferences)                                                                      |
| API              | [PokéAPI](https://pokeapi.co/) (main REST API) + [PokeKotlin](https://pokeapi.github.io/pokekotlin/) |
| Testing          | JUnit, MockK, kotlinx.coroutines test                                                                |

## 🏃 Getting Started

### Requirements

- Android Studio Giraffe or newer
- JDK 11
- Minimum SDK 24
- Target SDK 35

### Run Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Run the `:app` module on an emulator or physical device

Data is loaded on first app launch using `PokemonService.preloadModelsWithProgress()`, then cached in-memory for fast retrieval.

To reduce memory usage, flavor text is no longer preloaded. It is fetched dynamically when needed.

## 🧪 Testing

This project uses a combination of **unit tests** (`test/`) and **UI/functional tests** (`androidTest/`):

- `test/` contains fast, isolated unit tests for repository and service logic (e.g., validating Pokémon data loading and formatting).
- `androidTest/` includes instrumented Compose UI tests that verify screen behavior and navigation (e.g., switching tabs, showing Pokémon details).

All tests can be run directly from Android Studio or via Gradle using:

```bash
./gradlew test                   # Runs unit tests
./gradlew connectedAndroidTest  # Runs instrumented UI tests
```
