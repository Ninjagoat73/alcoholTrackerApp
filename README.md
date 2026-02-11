# Alcohol Tracker App

A side project for tracking alcohol consumption, expenses, and health-related metrics. This Android application allows users to log drinks, search for beer types, and visualize their habits through various analytical charts.

## Features

* **Drink Logging**: Easily record drinks with details such as category, amount, and date/time.
* **Analytics Dashboard**: Visualize consumption patterns, spending habits, and health impacts through interactive charts (Area, Bar, Pie, Heatmaps, etc.).
* **Alcohol Database**: Search for specific beers and drinks using an integrated search component and remote API.
* **User Profiles**: Track personal statistics and manage user preferences.
* **Secure Authentication**: Supports Firebase Authentication, including anonymous guest login and Google Sign-In.
* **Favorites & History**: Quickly log frequently or recently consumed drinks.

## Tech Stack

* **Language**: Kotlin
* **UI Framework**: Jetpack Compose
* **Dependency Injection**: Hilt
* **Navigation**: Jetpack Compose Navigation (Type-safe)
* **Database**: Room for local data persistence
* **Backend**: Firebase (Auth and Firestore)

## Project Structure

The application follows a clean architecture pattern:
* **`data/`**: Contains local Room databases, DAOs, repositories, and remote API sources.
* **`domain/`**: Includes business logic handlers for different drink categories (Beer, Wine, Spirits, etc.) and use cases.
* **`ui/`**: 
    * **`components/`**: Reusable UI elements like progress bars, navigation bars, and specialized graphs.
    * **`screens/`**: Main UI screens (Home, Analytics, List, Add Drink, Search).
    * **`viewmodel/`**: State management for UI components.
* **`utils/`**: Helper classes for dates and other common tasks.

## Getting Started

### Prerequisites
* Android Studio Ladybug or newer
* JDK 11
* Min SDK: 32 / Target SDK: 36

### Installation
1.  Clone the repository.
2.  Set up a Firebase project and add your `google-services.json` to the `app/` directory.
3.  Build and run the app on an emulator or physical device.

---
*This is a "side project for funsies."*
