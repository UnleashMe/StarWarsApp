package com.example.starwarsapp.domain.use_cases

class DatabaseUseCases(
    val faveCharacterUseCase: FaveCharacterUseCase,
    val unfaveCharacterUseCase: UnfaveCharacterUseCase,
    val favePlanetUseCase: FavePlanetUseCase,
    val unfavePlanetUseCase: UnfavePlanetUseCase,
    val faveStarshipUseCase: FaveStarshipUseCase,
    val unfaveStarshipUseCase: UnfaveStarshipUseCase,
    val getFavouriteCharactersUseCase: GetFavouriteCharactersUseCase,
    val getFavouriteStarshipsUseCase: GetFavouriteStarshipsUseCase,
    val getFavouritePlanetsUseCase: GetFavouritePlanetsUseCase
)