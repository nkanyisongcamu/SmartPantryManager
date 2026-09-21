# Smart Pantry Manager

Smart Pantry Manager is a Java Android application for reducing food waste by tracking ingredients available in a user's pantry and showing recipes only when all required ingredients are available.

## Technology
- Java
- Android Studio
- SQLite using SQLiteOpenHelper
- RecyclerView with custom adapters
- Android Activities and Intents

## Database choice
SQLite was selected because the application is designed around a user's local pantry. It is simple to implement, works offline, persists data after the app closes, and is suitable for the assignment's CRUD requirement.

## Main screens
1. Pantry List
2. Add/Edit Ingredient
3. Suggested Recipes
4. Recipe Detail
5. Settings

## Strict matching
A recipe is included in Suggested Recipes only when every ingredient listed for that recipe is present in the pantry with a quantity of at least 1. Partial matches are excluded.

The database also normalizes simple ingredient names so common singular/plural differences such as `tomato` and `tomatoes` can match.

## Run instructions
1. Open this project folder in Android Studio.
2. Allow Gradle to sync.
3. Use an Android emulator or connected Android device.
4. Run the `app` configuration.
5. Add pantry ingredients and open Suggested Recipes.

## GitHub
Create a public GitHub repository and push this project from the beginning of development. Add at least 10 genuine incremental commits. Replace this section with the actual repository link before submission.

## Important
The assignment requires actual screenshots of the running application, a 5-7 minute narrated demonstration video, a public GitHub repository, and a written report. Those evidence items must be produced from your own running project and development process.

## Project Features

- Add ingredients to the pantry
- Edit pantry ingredients
- Delete pantry ingredients
- View available ingredients
- Manage pantry information
- Android-based user interface
