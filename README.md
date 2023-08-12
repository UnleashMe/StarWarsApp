# StarWarsApp

This app allows users to search for characters, planets and starships and save them to their favourites.

## Technologies I've used in this project
- Language: Kotlin
- Architecture: MVI
- UI: Compose
- Coroutines
- Room
- Retrofit
- Hilt

## Content

The app consists of 2 screens: search and favourites. Search screen shows textField and when user types at least two symbols displays a list of Star Wars characters, planets and starships, which name contains the input. There's an option to add them to favourites. Favourites screen shows the list of favourites and then films, in which those favourites were.

## How it works

All data comes from https://swapi.dev/api/. When user types something in search field, app makes 3 Api calls to search for characters, planets and starships. The answer  contains no more than 10 items, so if there's more, then additional calls will be performed. On add to favourite button the item is saved to local database, and for favourites screen the data comes from database, although the films are queried from Api based on items from database, so it takes a bit more time to download films. There is also Unit tests, which I've written for the first time.
