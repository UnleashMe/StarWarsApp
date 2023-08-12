package com.example.starwarsapp.domain.use_cases

class NetworkUseCases(
    val searchForCharactersUseCase: SearchForCharactersUseCase,
    val searchForStarshipsUseCase: SearchForStarshipsUseCase,
    val searchForPlanetsUseCase: SearchForPlanetsUseCase,
    val searchForFilmsUseCase: SearchForFilmsUseCase
)